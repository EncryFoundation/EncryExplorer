package controllers

import java.text.SimpleDateFormat
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.{Header, HistoryDao}
import play.api.libs.circe.Circe
import play.api.mvc._
import utils._
import views.html.{headersAtView, headersByCountView, headersByDateView, headersByRangeView, lastHeadersView, getHeader => getHeaderView, headersByDateFromTo}
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

  private def sortedWith(mode: String, list: List[Header]): List[Header] = mode match {
    case "byHeight" => list.sortWith(_.height > _.height)
    case "byDate" => list.sortWith(_.timestamp > _.timestamp)
    case "byTxs" => list.sortWith(_.txsQty > _.txsQty)
    case "byBlockSize" => list.sortWith(_.blockSize > _.blockSize)
    case "byTxsSize" => list.sortWith(_.txsSize > _.txsSize)
    case _ => throw new IllegalArgumentException
  }

  def listLastHeadersView(mode: String, param: Int): Action[AnyContent] = heightCheck(param).async {
    historyDao.listLastHeadersByHeight(param)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(lastHeadersView(list, param))
      }
  }

  def listLastHeadersApi(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao
      .listLastHeadersByHeight(qty)
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(list.asJson)
      }
  }

  def listHeadersAtHeightView(mode: String, param: Int): Action[AnyContent] = heightCheck(param).async {
    historyDao.listHeadersAtHeight(param)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(headersAtView(list, param))
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

  def listHeadersByCountView(mode: String, from: Int, count: Int): Action[AnyContent] = fromCountCheck(from, count).async {
    historyDao.listHeadersByCount(from, count)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(headersByCountView(list, from, count))
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

  def findHeadersByDateView(mode: String, date: String, count: Int): Action[AnyContent] = dateFromCount(date, count).async {
    historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(headersByDateView(list, date, count))
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

  def listHeadersByHeightRangeView(mode: String, from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao.listHeadersByHeightRange(from, to)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(headersByRangeView(list, from, to))
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

  def listHeadersByDateFromToView(mode: String, fromDate: String, toDate: String): Action[AnyContent] = dateFromToCheck(fromDate, toDate).async {
    historyDao
      .findHeadersByFromToDate(sdf.parse(fromDate + " 00:00:00").getTime, sdf.parse(toDate + " 23:59:59").getTime)
      .map(list => sortedWith(mode, list))
      .map {
        case Nil => NotFound
        case list: List[Header] => Ok(headersByDateFromTo(list, fromDate, toDate))
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

}