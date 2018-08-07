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

@Singleton
class HistoryController @Inject()(cc: ControllerComponents,
                                  historyDao: HistoryDao,
                                  base16Check: Base16CheckActionFactory,
                                  heightCheck: HeightCheckActionFactory,
                                  fromToCheck: FromToCheckActionFactory,
                                  qtyCheck: QtyCheckActionFactory,
                                  fromCountCheck: FromCountCheckActionFactory,
                                  dateFromCount: DateFromCountActionFactory,
                                  dateFromToCheck: DateFromToCheckActionFactory)
                                 (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

  def findHeaderView(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao
      .findHeader(id)
      .map {
        case Some(header) => Ok(getHeaderView(header))
        case None => NotFound
      }
  }

  def findHeaderApi(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao
      .findHeader(id)
      .map {
        case Some(header) => Ok(header.asJson)
        case None => NotFound
      }
  }

  def listHeadersAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .listHeadersAtHeight(height)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def listHeadersAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .listHeadersAtHeight(height)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def findBestHeaderAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .findBestHeaderAtHeight(height)
      .map {
        case Some(header) => Ok(header.asJson)
        case None => NotFound
      }
  }

  def findBestHeaderAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao
      .findBestHeaderAtHeight(height)
      .map {
        case Some(header) => Ok(getHeaderView(header))
        case None => NotFound
      }
  }

  def listLastHeadersApi(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao
      .listLastHeaders(qty)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def listLastHeadersView(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao
      .listLastHeaders(qty)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def listHeadersByCountView(from: Int, count: Int): Action[AnyContent] = fromCountCheck(from, count).async {
    historyDao
      .listHeadersByCount(from, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def listHeadersByCountApi(from: Int, count: Int): Action[AnyContent] = fromCountCheck(from, count).async {
    historyDao
      .listHeadersByCount(from, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao
      .listHeadersByHeightRange(from, to)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao
      .listHeadersByHeightRange(from, to)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def listHeadersByDateFromToView(fromDate: String, toDate: String): Action[AnyContent] = dateFromToCheck(fromDate, toDate).async {
    historyDao
      .findHeadersByFromToDate(sdf.parse(fromDate + " 00:00:00").getTime, sdf.parse(toDate + " 23:59:59").getTime)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def listHeadersByDateFromToApi(fromDate: String, toDate: String): Action[AnyContent] = dateFromToCheck(fromDate, toDate).async {
    historyDao
      .findHeadersByFromToDate(sdf.parse(fromDate + " 00:00:00").getTime, sdf.parse(toDate + " 23:59:59").getTime)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def findHeadersByDateView(date: String, count: Int): Action[AnyContent] = dateFromCount(date, count).async {
    historyDao
      .findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(getHeaderListView(list))
      }
  }

  def findHeadersByDateApi(date: String, count: Int): Action[AnyContent] = dateFromCount(date, count).async {
    historyDao
      .findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

}
