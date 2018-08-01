package controllers

import java.text.SimpleDateFormat
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.{Header, HistoryDao}
import play.api.libs.circe.Circe
import play.api.mvc._
import utils._
import views.html.{getHeader => getHeaderView, getHeaderList => getHeaderListView}
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

@Singleton
class HistoryController @Inject()(cc: ControllerComponents,
                                  historyDao: HistoryDao,
                                  base16Check: Base16CheckActionFactory,
                                  heightCheck: HeightCheckActionFactory,
                                  fromToCheck: FromToCheckActionFactory,
                                  qtyCheck: QtyCheckActionFactory,
                                  fromCountCheck: FromCountCheckActionFactory,
                                  dateFromCount: DateFromCountActionFactory,
                                 )
                                 (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

  def findHeaderView(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao
      .findHeader(id)
      .map {
        case Some(header) => Ok(getHeaderView(header))
        case None => NotFound
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findHeaderApi(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao
      .findHeader(id)
      .map {
        case Some(header) => Ok(header.asJson)
        case None => NotFound
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .listHeadersAtHeight(height)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .listHeadersAtHeight(height)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findBestHeaderAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .findBestHeaderAtHeight(height)
      .map {
        case Some(header) => Ok(header.asJson)
        case None => NotFound
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findBestHeaderAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .findBestHeaderAtHeight(height)
      .map {
        case Some(header) => Ok(getHeaderView(header))
        case None => NotFound
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listLastHeadersApi(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao
      .listLastHeaders(qty)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listLastHeadersView(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao
      .listLastHeaders(qty)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersByCountView(from: Int, count: Int): Action[AnyContent] = fromToCheck(from, count).async {
    historyDao
      .listHeadersByCount(from, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersByCountApi(from: Int, count: Int): Action[AnyContent] = fromToCheck(from, count).async {
    historyDao
      .listHeadersByCount(from, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao
      .listHeadersByHeightRange(from, to)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao
      .listHeadersByHeightRange(from, to)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findHeadersByDateView(date: String, count: Int): Action[AnyContent] = dateFromCount(date, count).async {
    historyDao
      .findHeadersByDate(sdf.parse(date + " 23:59:59").getTime, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }

  def findHeadersByDateApi(date: String, count: Int): Action[AnyContent] = dateFromCount(date, count).async {
    historyDao
      .findHeadersByDate(sdf.parse(date + " 23:59:59").getTime, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
      .recover {
        case NonFatal(_) => BadRequest
      }
  }
}
