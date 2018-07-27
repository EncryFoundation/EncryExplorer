package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.{Block, Header, Transaction}
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{HistoryService, TransactionsService}
import views.html.getBlock
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BlockController @Inject()
(cc: ControllerComponents, historyService: HistoryService, transactionsService: TransactionsService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findBlockView(id: String): Action[AnyContent] = Action.async {
    findBlock(id).map {
      case Some(block) => Ok(getBlock(block))
      case None => NotFound
    }
  }

  def findBlockApi(id: String): Action[AnyContent] = Action.async {
    findBlock(id).map {
      case Some(block) => Ok(block.asJson)
      case None => NotFound
    }
  }

  def findBlock(id: String): Future[Option[Block]] = {
    val headerFuture: Future[Option[Header]] = historyService.findHeader(id)
    val payloadFuture: Future[List[Transaction]] = transactionsService.listTransactionsByBlockId(id)
    for {
      headerOpt <- headerFuture
      payload <- payloadFuture
    } yield headerOpt match {
      case Some(header) => Some(Block(header, payload))
      case None => None
    }
  }

}
