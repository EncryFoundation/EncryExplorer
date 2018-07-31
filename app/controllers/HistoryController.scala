package controllers

import java.text.SimpleDateFormat
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.Header
import play.api.libs.circe.Circe
import play.api.mvc._
import services.HistoryService
import views.html.{getHeader => getHeaderView, getHeaderList => getHeaderListView}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try
import scala.util.control.NonFatal

@Singleton
class HistoryController @Inject()(cc: ControllerComponents, historyService: HistoryService)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with Circe {

  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

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

  def findHeadersByCountView(from: Int, count: Int): Action[AnyContent] = Action.async {
    historyService.findHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def findHeadersByCountApi(from: Int, count: Int): Action[AnyContent] = Action.async {
    historyService.findHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
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
      case list: List[Header] => Ok(list.asJson)
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
      case list: List[Header] => Ok(list.asJson)
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

  def listHeadersByDateFromToView(fromDate: String, toDate: String): Action[AnyContent] = Action.async {
    Future.fromTry(
      Try(sdf.parse(fromDate + " 00:00:00").getTime, sdf.parse(toDate + " 23:59:59").getTime)
    ).flatMap { date =>
      historyService.findHeadersByFromToDate(date._1, date._2)
    }.map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def listHeadersByDateFromToApi(fromDate: String, toDate: String): Action[AnyContent] = Action.async {
    Future.fromTry(
      Try(sdf.parse(fromDate + " 00:00:00").getTime, sdf.parse(toDate + " 23:59:59").getTime)
    ).flatMap { date => 
      historyService.findHeadersByFromToDate(date._1, date._2)
    }.map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def findHeadersByDateView(date: String, count: Int): Action[AnyContent] = Action.async {
    Future.fromTry(
      Try(sdf.parse(date + " 23:59:59"))
    ).flatMap { date =>
      historyService.findHeadersByDate(date.getTime, count)
    }.map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

  def findHeadersByDateApi(date: String, count: Int): Action[AnyContent] = Action.async {
    Future.fromTry(
      Try(sdf.parse(date + " 23:59:59"))
    ).flatMap { date =>
      historyService.findHeadersByDate(date.getTime, count)
    }.map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }.recover {
      case NonFatal(_) => BadRequest
    }
  }

}
