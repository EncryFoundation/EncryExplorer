package controllers

import io.circe.syntax._
import javax.inject.Inject
import models._
import play.api.libs.circe.Circe
import play.api.mvc._
import protocol.{EncryAddress, Pay2ContractHashAddress, Pay2PubKeyAddress, PubKeyLockedContract}
import scorex.crypto.encode.Base16
import utils.{Base16CheckActionFactory, AddressCheckActionFactory, FromToCheckActionFactory, HeightCheckActionFactory}
import views.html.{getTransactions, getTransactionsList}
import scala.concurrent.ExecutionContext

class TransactionsController @Inject()(cc: ControllerComponents,
                                       transactionsDao: TransactionsDao,
                                       base16Check: Base16CheckActionFactory,
                                       heightCheck: HeightCheckActionFactory,
                                       fromToCheck: FromToCheckActionFactory,
                                       addressCheck: AddressCheckActionFactory)
                                      (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  def findOutputApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutput(id)
      .map(output => Ok(output.asJson))
  }

  def listOutputsByAddressApi(address: String): Action[AnyContent] = addressCheck(address).async {
    transactionsDao
      .listOutputsByContractHash(contractHashByAddress(address), unspentOnly = false)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def listUnspentOutputsByAddressApi(address: String): Action[AnyContent] = addressCheck(address).async {
    transactionsDao
      .listOutputsByContractHash(contractHashByAddress(address), unspentOnly = true)
      .map(outputs => Ok(outputs.asJson))
  }

  private def contractHashByAddress(address: String): String = EncryAddress.resolveAddress(address).map {
    case p2pk: Pay2PubKeyAddress => PubKeyLockedContract(p2pk.pubKey).contractHashHex
    case p2sh: Pay2ContractHashAddress => Base16.encode(p2sh.contractHash)
  }.getOrElse(throw EncryAddress.InvalidAddressException)

  def findOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
  }

  def findUnspentOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
  }

  def findInputApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findInput(id)
      .map(inputs => Ok(inputs.asJson))
  }

  def listInputsByTxIdApi(txId: String): Action[AnyContent] = base16Check(txId).async {
    transactionsDao
      .listInputs(txId)
      .map(inputs => Ok(inputs.asJson))
  }

  def findTransactionApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id)
      .map {
        case Some(transaction) => Ok(transaction.asJson)
        case None => NotFound
      }
  }

  def findTransactionView(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id)
      .map {
        case Some(transaction) => Ok(getTransactions(transaction))
        case None => NotFound
      }
  }

  def listByBlockIdApi(blockId: String): Action[AnyContent] = base16Check(blockId).async {
    transactionsDao
      .listByBlockId(blockId)
      .map {
        case Nil => NotFound
        case list: List[Transaction] => Ok(list.asJson)
      }
  }

  def listByBlockIdView(blockId: String): Action[AnyContent] = base16Check(blockId).async {
    transactionsDao
      .listByBlockId(blockId)
      .map {
        case Nil => NotFound
        case list: List[Transaction] => Ok(getTransactionsList(list))
      }
  }

  def outputsByBlockHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    transactionsDao
      .listOutputsByBlockHeight(height)
      .map(tx => Ok(tx.asJson))
  }

  def unspentOutputsByBlockHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    transactionsDao
      .listOutputsByBlockHeightUnspent(height)
      .map(tx => Ok(tx.asJson))
  }

  def findOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
  }

  def findUnspentOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
  }

  def findTransactionByBlockHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    transactionsDao
      .findTransactionByBlockHeightRange(from, to)
      .map(tx => Ok(tx.asJson))
  }

}
