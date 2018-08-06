package controllers

import java.text.SimpleDateFormat
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.{Header, HistoryDao}
import play.api.libs.circe.Circe
import play.api.mvc._
import utils._
import views.html.{headersAtView, headersByCountView, headersByDateView, headersByRangeView, lastHeadersView, getHeader => getHeaderView}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HistoryController @Inject()(cc: ControllerComponents,
                                  historyDao: HistoryDao,
                                  base16Check: Base16CheckActionFactory,
                                  heightCheck: HeightCheckActionFactory,
                                  fromToCheck: FromToCheckActionFactory,
                                  qtyCheck: QtyCheckActionFactory,
                                  fromCountCheck: FromCountCheckActionFactory,
                                  dateFromCount: DateFromCountActionFactory)
                                 (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  private val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

  def listLastHeadersView(mode: String, param: Int): Action[AnyContent] = heightCheck(param).async {
    val result: Future[Product] = mode match {
      case "byHeight" => historyDao.listLastHeadersByHeight(param)
      case "byDate" => historyDao.listLastHeadersByHeight(param).map(_.sortWith(_.timestamp > _.timestamp))
      case "byTxs" => historyDao.listLastHeadersByHeight(param).map(_.sortWith(_.txsQty > _.txsQty))
      case "byBlockSize" => historyDao.listLastHeadersByHeight(param).map(_.sortWith(_.blockSize > _.blockSize))
      case "byTxsSize" => historyDao.listLastHeadersByHeight(param).map(_.sortWith(_.txsSize > _.txsSize))
      case _ => Future(None)
    }
    result.map {
      case Nil => NotFound
      case list: List[Header] => Ok(lastHeadersView(list, param))
      case None => BadRequest
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
    val result: Future[Product] = mode match {
      case "byHeight" => historyDao.listHeadersAtHeight(param)
      case "byDate" => historyDao.listHeadersAtHeight(param).map(_.sortWith(_.timestamp > _.timestamp))
      case "byTxs" => historyDao.listHeadersAtHeight(param).map(_.sortWith(_.txsQty > _.txsQty))
      case "byBlockSize" => historyDao.listHeadersAtHeight(param).map(_.sortWith(_.blockSize > _.blockSize))
      case "byTxsSize" => historyDao.listHeadersAtHeight(param).map(_.sortWith(_.txsSize > _.txsSize))
      case _ => Future(None)
    }
    result.map {
      case Nil => NotFound
      case list: List[Header] => Ok(headersAtView(list, param))
      case None => BadRequest
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
    val result: Future[Product] = mode match {
      case "byHeight" => historyDao.listHeadersByCount(from, count)
      case "byDate" => historyDao.listHeadersByCount(from, count).map(_.sortWith(_.timestamp > _.timestamp))
      case "byTxs" => historyDao.listHeadersByCount(from, count).map(_.sortWith(_.txsQty > _.txsQty))
      case "byBlockSize" => historyDao.listHeadersByCount(from, count).map(_.sortWith(_.blockSize > _.blockSize))
      case "byTxsSize" => historyDao.listHeadersByCount(from, count).map(_.sortWith(_.txsSize > _.txsSize))
      case _ => Future(None)
    }
    result.map {
      case Nil => NotFound
      case list: List[Header] => Ok(headersByCountView(list, from, count))
      case None => BadRequest
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
    val result: Future[Product] = mode match {
      case "byHeight" => historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count)
      case "byDate" => historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count).map(_.sortWith(_.timestamp > _.timestamp))
      case "byTxs" => historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count).map(_.sortWith(_.txsQty > _.txsQty))
      case "byBlockSize" => historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count).map(_.sortWith(_.blockSize > _.blockSize))
      case "byTxsSize" => historyDao.findHeadersByDate(sdf.parse(date + " 0:0:0").getTime, count).map(_.sortWith(_.txsSize > _.txsSize))
      case _ => Future(None)
    }
    result.map {
      case Nil => NotFound
      case list: List[Header] => Ok(headersByDateView(list, date, count))
      case None => BadRequest
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
    val result: Future[Product] = mode match {
      case "byHeight" => historyDao.listHeadersByHeightRange(from, to)
      case "byDate" => historyDao.listHeadersByHeightRange(from, to).map(_.sortWith(_.timestamp > _.timestamp))
      case "byTxs" => historyDao.listHeadersByHeightRange(from, to).map(_.sortWith(_.txsQty > _.txsQty))
      case "byBlockSize" => historyDao.listHeadersByHeightRange(from, to).map(_.sortWith(_.blockSize > _.blockSize))
      case "byTxsSize" => historyDao.listHeadersByHeightRange(from, to).map(_.sortWith(_.txsSize > _.txsSize))
      case _ => Future(None)
    }
    result.map {
      case Nil => NotFound
      case list: List[Header] => Ok(headersByRangeView(list, from, to))
      case None => BadRequest
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
