package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Header
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import views.html.{getHeader,getHeaderList}
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findHeaderView(id: String): Action[AnyContent] = Action.async {
    historyService.getHeader(id).map {
      case Some(header) => Ok(getHeader(header))
      case None => NotFound
    }
  }

  def findHeaderApi(id: String): Action[AnyContent] = Action.async {
    historyService.getHeader(id).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
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

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService.getHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService.getHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderList(list))
    }
  }

  def findHeadersByCountView(from: Int, count: Int): Action[AnyContent] = Action.async {
    historyService.findHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderList(list))
    }
  }

  def findHeadersByCountApi(from: Int, count: Int): Action[AnyContent] = Action.async {
    historyService.findHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

}
