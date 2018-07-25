package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findHeader(id: String): Action[AnyContent] = Action.async {
    historyService
      .findHeader(id)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .listHeadersAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findBestHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .findBestHeaderAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listLastHeaders(qty: Int): Action[AnyContent] = Action.async {
    historyService
      .listLastHeaders(qty)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersByHeightRange(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService
      .listHeadersByHeightRange(from, to)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

}
