package controllers

import io.circe.syntax._
import javax.inject.Inject
import models.Block
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{HistoryService, TransactionsService}
import scala.concurrent.ExecutionContext
import views.html.{getBlock => getBlockView}


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
      case Some(header) => Ok(getBlockView(Block(header, payload)))
      case None => NotFound
    }
  }

}
