package controllers

import io.circe.syntax._
import io.circe.generic.auto._
import javax.inject.Inject
import models._
import org.encryfoundation.common.transaction.{EncryAddress, Pay2ContractHashAddress, Pay2PubKeyAddress, PubKeyLockedContract}
import play.api.libs.circe.Circe
import play.api.mvc._
import scorex.crypto.encode.Base16
import utils.{AddressCheckActionFactory, Base16CheckActionFactory, FromToCheckActionFactory, HeightCheckActionFactory}
import views.html.{getTransactions, getTransactionsList}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

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
      .map {
        case Some(output) => Ok(output.asJson)
        case None => NotFound
      }
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
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(Random.shuffle(list).take(300).asJson)
      }
  }

  private def contractHashByAddress(address: String): String = EncryAddress.resolveAddress(address).map {
    case p2pk: Pay2PubKeyAddress => PubKeyLockedContract(p2pk.pubKey).contractHashHex
    case p2sh: Pay2ContractHashAddress => Base16.encode(p2sh.contractHash)
  }.getOrElse(throw EncryAddress.InvalidAddressException)

  def findOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputsByTxId(id)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def findTransactionWithOutputsInputsView(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id)
      .flatMap {
        case Some(tx) => transactionsDao.findOutputsByTxId(id).flatMap { out =>
          transactionsDao.listInputs(id).map { in => Ok(getTransactions(tx, out, in))
          }
        }
        case None => Future.successful(NotFound)
      }
  }

  def findUnspentOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputsByTxId(id)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def findInputApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findInput(id)
      .map {
        case Some(input) => Ok(input.asJson)
        case None => NotFound
      }
  }

  def listInputsByTxIdApi(txId: String): Action[AnyContent] = base16Check(txId).async {
    transactionsDao
      .listInputs(txId)
      .map {
        case Nil => NotFound
        case list: List[Input] => Ok(list.asJson)
      }
  }

  def findTransactionApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id)
      .map {
        case Some(transaction) => Ok(transaction.asJson)
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
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def unspentOutputsByBlockHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    transactionsDao
      .listOutputsByBlockHeightUnspent(height)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def findOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputByBlockId(id)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def findUnspentOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputByBlockId(id)
      .map {
        case Nil => NotFound
        case list: List[Output] => Ok(list.asJson)
      }
  }

  def findTransactionByBlockHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    transactionsDao
      .findTransactionByBlockHeightRange(from, to)
      .map {
        case Nil => NotFound
        case list: List[Transaction] => Ok(list.asJson)
      }
  }

}
