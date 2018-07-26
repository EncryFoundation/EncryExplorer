package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Block
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{HistoryService, TransactionsService}
import scala.concurrent.ExecutionContext

@Singleton
class BlockController @Inject()
(cc: ControllerComponents, historyService: HistoryService, transactionsService: TransactionsService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findBlock(id: String): Action[AnyContent] = Action.async {
    val headerFuture = historyService.findHeader(id)
    val payloadFuture = transactionsService.listTransactionsByBlockId(id)
    for {
      headerOpt <- headerFuture
      payload <- payloadFuture
    } yield headerOpt match {
      case Some(header) => Ok(Block(header, payload).asJson)
      case None => NotFound
    }
  }

}
