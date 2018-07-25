package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal
import views.html.{getHeader => getViewHeader}
import views.html.{getHeaderList => getViewHeaderList}

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def getHeader(id: String): Action[AnyContent] = Action.async {
    historyService
      .getHeader(id)
      .map(header => Ok(getViewHeader(header.get)))
      .recover {
        case NonFatal(_) => BadRequest("123")
      }
  }

  def getHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .getHeadersAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def getBestHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .getBestHeaderAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def getLastHeaders(qty: Int): Action[AnyContent] = Action.async {
    historyService
      .getLastHeaders(qty)
      .map(header => Ok(header.asJson))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def getHeadersByHeightRange(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService
      .getHeadersByHeightRange(from, to)
      .map(header => Ok(getViewHeaderList(header)))
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

}
