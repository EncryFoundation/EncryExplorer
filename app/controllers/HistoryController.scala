package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Header
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import views.html.{getHeader => getHeaderView, getHeaderList => getHeaderListView}
import scala.concurrent.ExecutionContext

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findHeaderView(id: String): Action[AnyContent] = Action.async {
    historyService.findHeader(id).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def findHeaderApi(id: String): Action[AnyContent] = Action.async {
    historyService.findHeader(id).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
    }
  }

  def findHeaderAtHeightView(height: Int): Action[AnyContent] = Action.async {
    historyService.listHeadersAtHeight(height).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def findHeaderAtHeightApi(height: Int): Action[AnyContent] = Action.async {
    historyService.listHeadersAtHeight(height).map {
      case Nil => NotFound
      case list: List[Header]  => Ok(list.asJson)
    }
  }

  def findBestHeaderAtHeightApi(height: Int): Action[AnyContent] = Action.async {
    historyService.findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
    }
  }

  def findBestHeaderAtHeightView(height: Int): Action[AnyContent] = Action.async {
    historyService.findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def listLastHeadersApi(qty: Int): Action[AnyContent] = Action.async {
    historyService.listLastHeaders(qty).map {
      case Nil => NotFound
      case list: List[Header]  => Ok(list.asJson)
    }
  }

  def listLastHeadersView(qty: Int): Action[AnyContent] = Action.async {
    historyService.listLastHeaders(qty).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService.listHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = Action.async {
    historyService.listHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

}
