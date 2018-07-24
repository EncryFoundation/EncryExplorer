package controllers

import io.circe.syntax._
import javax.inject.Inject
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.TransactionsService
import io.circe.syntax._
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

class TransactionsController @Inject()(cc: ControllerComponents, transactionsService: TransactionsService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findOutput(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findOutput(id)
      .map(output => Ok(output.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listOutputsByAddress(address: String): Action[AnyContent] = Action.async {
    transactionsService
      .listOutputsByAddress(address)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listUnspentOutputsByAddress(address: String): Action[AnyContent] = Action.async {
    transactionsService
      .listOutputsByAddress(address, unspentOnly = true)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findOutputsByTxId(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findUnspentOutputsByTxId(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findUnspentOutputsByTxId(id)
      .map(outputs => Ok(outputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findInput(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findInput(id)
      .map(inputs => Ok(inputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listInputsByTxId(txId: String): Action[AnyContent] = Action.async {
    transactionsService
      .listInputs(txId)
      .map(inputs => Ok(inputs.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findTransaction(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findTransaction(id)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }


  def findOutputByBlockId(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findUnspentOutputByBlockId(id: String): Action[AnyContent] = Action.async {
    transactionsService
      .findUnspentOutputByBlockId(id)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findTransactionByBlockHeightRange(from: Int, to: Int): Action[AnyContent] = Action.async {
    transactionsService
      .findTransactionByBlockHeightRange(from, to)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listByBlockId(blockId: String): Action[AnyContent] = Action.async {
    transactionsService
      .listTransactionsByBlockId(blockId)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def outputsByBlockHeight(height: Int): Action[AnyContent] = Action.async {
    transactionsService
      .listOutputsByBlockHeight(height)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def unspentOutputsByBlockHeight(height: Int): Action[AnyContent] = Action.async {
    transactionsService
      .listOutputsByBlockHeightUnspent(height)
      .map(tx => Ok(tx.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

}
