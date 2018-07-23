package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService

import scala.concurrent.ExecutionContext

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def getHeader(id: String): Action[AnyContent] = Action.async {
    historyService
      .getHeader(id)
      .map(header => Ok(header.asJson))
      .recover {
        case _ => BadRequest
      }
  }

  def getHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .getHeadersAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case _ => BadRequest
      }
  }

  def getBestHeaderAtHeight(height: Int): Action[AnyContent] = Action.async {
    historyService
      .getBestHeaderAtHeight(height)
      .map(header => Ok(header.asJson))
      .recover {
        case _ => BadRequest
      }
  }

  def getLastHeaders(height: Int): Action[AnyContent] = Action.async {
    historyService
      .getLastHeaders(height)
      .map(header => Ok(header.asJson))
      .recover {
        case _ => BadRequest
      }
  }

  def getHeadersByHeightRange(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService
      .getHeadersByHeightRange(from, to)
      .map(header => Ok(header.asJson))
      .recover {
        case _ => BadRequest
      }
  }

}
