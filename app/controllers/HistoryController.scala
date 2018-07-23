package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.services.HistoryControllerService
import play.api.libs.circe.Circe
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, hcs: HistoryControllerService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def getHeaderR(id: String): Action[AnyContent] = Action.async {
    hcs.getHeaderR(id).map(a => Ok(a.asJson)).recover { case _ => BadRequest(s"Bad request") }
  }

  def getHeadersAtHeightR(height: String): Action[AnyContent] = Action.async {
    hcs.getHeadersAtHeightR(height).map(a => Ok(a.asJson)).recover { case _ => BadRequest(s"Bad request") }
  }

  def getBestHeaderAtHeightR(height: String): Action[AnyContent] = Action.async {
    hcs.getBestHeaderAtHeightR(height).map(a => Ok(a.asJson)).recover { case _ => BadRequest(s"Bad request") }
  }

  def getLastHeadersR(qty: String): Action[AnyContent] = Action.async {
    hcs.getLastHeadersR(qty).map(a => Ok(a.asJson)).recover { case _ => BadRequest(s"Bad request") }
  }

//  def getHeadersByHeightRangeR(from: String, to: String): Action[AnyContent] = Action.async {
//    hcs.getHeadersByHeightRangeR(from, to)
//  }

}
