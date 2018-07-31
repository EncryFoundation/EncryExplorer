package controllers

import io.circe.syntax._
import javax.inject.Inject
import models.{Transaction, TransactionsDao}
import play.api.libs.circe.Circe
import play.api.mvc._
import protocol.AccountLockedContract
import utils.{Base16CheckActionFactory, Base58CheckActionFactory, FromToCheckActionFactory, HeightCheckActionFactory}
import views.html.{getTransactions, getTransactionsList}
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

class TransactionsController @Inject()(cc: ControllerComponents,
                                       transactionsDao: TransactionsDao,
                                       base16Check: Base16CheckActionFactory,
                                       heightCheck: HeightCheckActionFactory,
                                       fromToCheck: FromToCheckActionFactory,
                                       base58Check: Base58CheckActionFactory,
                                      )
                                      (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  def findOutputApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutput(id)
      .map(output => Ok(output.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listOutputsByAddressApi(address: String): Action[AnyContent] = base58Check(address).async {
    transactionsDao
      .listOutputsByContractHash(contractHashByAddress(address), unspentOnly = false)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listUnspentOutputsByAddressApi(address: String): Action[AnyContent] = base58Check(address).async {
    transactionsDao
      .listOutputsByContractHash(contractHashByAddress(address), unspentOnly = true)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex

  def findOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findUnspentOutputsByTxIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findInputApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findInput(id)
      .map(inputs => Ok(inputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listInputsByTxIdApi(txId: String): Action[AnyContent] = base16Check(txId).async {
    transactionsDao
      .listInputs(txId)
      .map(inputs => Ok(inputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findTransactionApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id).map {
      case Some(transaction) => Ok(transaction.asJson)
      case None => NotFound
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def findTransactionView(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findTransaction(id).map {
      case Some(transaction) => Ok(getTransactions(transaction))
      case None => NotFound
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def listByBlockIdApi(blockId: String): Action[AnyContent] = base16Check(blockId).async {
    transactionsDao
      .listByBlockId(blockId).map {
      case Nil => NotFound
      case list: List[Transaction] => Ok(list.asJson)
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def listByBlockIdView(blockId: String): Action[AnyContent] = base16Check(blockId).async {
    transactionsDao
      .listByBlockId(blockId).map {
      case Nil => NotFound
      case list: List[Transaction] => Ok(getTransactionsList(list))
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def outputsByBlockHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    transactionsDao
      .listOutputsByBlockHeight(height)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def unspentOutputsByBlockHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    transactionsDao
      .listOutputsByBlockHeightUnspent(height)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findUnspentOutputByBlockIdApi(id: String): Action[AnyContent] = base16Check(id).async {
    transactionsDao
      .findUnspentOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findTransactionByBlockHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    transactionsDao
      .findTransactionByBlockHeightRange(from, to)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

}
