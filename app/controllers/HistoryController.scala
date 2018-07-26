package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Header
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import views.html.{getHeader => getHeaderView, getHeaderList => getHeaderListView}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  def findHeader(id: String): Future[Option[Header]] = {
    historyService
      .findHeader(id).map {
      case Some(header) => Some(header)
      case None => None
    }
  }

  def findHeaderView(id: String): Action[AnyContent] = Action.async {
    findHeader(id).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def findHeaderApi(id: String): Action[AnyContent] = Action.async {
    findHeader(id).map {
      case Some(block) => Ok(block.asJson)
      case None => NotFound
    }
  }

  def findHeaderAtHeight(height: Int): Future[List[Header]] = historyService.listHeadersAtHeight(height)

  def findHeaderAtHeightView(height: Int): Action[AnyContent] = Action.async {
    findHeaderAtHeight(height).map {
      case List(header) => Ok(getHeaderListView(List(header)))
      case Nil => NotFound
    }
  }

  def findHeaderAtHeightApi(height: Int): Action[AnyContent] = Action.async {
    findHeaderAtHeight(height).map {
      case List(header) => Ok(header.asJson)
      case Nil => NotFound
    }
  }

  def findBestHeaderAtHeight(height: Int): Future[Option[Header]] = {
    historyService
      .findBestHeaderAtHeight(height).map {
      case Some(header) => Some(header)
      case None => None
    }
  }

  def findBestHeaderAtHeightApi(height: Int): Action[AnyContent] = Action.async {
    findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
    }
  }

  def findBestHeaderAtHeightView(height: Int): Action[AnyContent] = Action.async {
    findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def listLastHeaders(qty: Int): Future[List[Header]] = historyService.listLastHeaders(qty)

  def listLastHeadersApi(qty: Int): Action[AnyContent] = Action.async {
    listLastHeaders(qty).map {
      case List(header) => Ok(header.asJson)
      case Nil => NotFound
    }
  }

  def listLastHeadersView(qty: Int): Action[AnyContent] = Action.async {
    listLastHeaders(qty).map {
      case List(header) => Ok(getHeaderListView(List(header)))
      case Nil => NotFound
    }
  }


  def listHeadersByHeightRange(from: Int, to: Int): Future[List[Header]] = historyService.listHeadersByHeightRange(from, to)

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = Action.async {
    listHeadersByHeightRange(from, to).map {
      case List(header) => Ok(getHeaderListView(List(header)))
      case Nil => NotFound
    }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = Action.async {
    listHeadersByHeightRange(from, to).map {
      case List(header) => Ok(getHeaderListView(List(header)))
      case Nil => NotFound
    }
  }

}
