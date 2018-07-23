package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import services.HistoryService
import play.api.libs.circe.Circe
import play.api.mvc._
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
}
