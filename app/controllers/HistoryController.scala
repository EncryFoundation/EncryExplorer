package controllers

import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import models.{Header, HistoryDao}
import play.api.libs.circe.Circe
import play.api.mvc._
import services._
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
                                 )
                                 (implicit ex: ExecutionContext) extends AbstractController(cc) with Circe {

  def findHeaderView(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao.findHeader(id).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def findHeaderApi(id: String): Action[AnyContent] = base16Check(id).async {
    historyDao.findHeader(id).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
    }
  }

  def listHeadersAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao.listHeadersAtHeight(height).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def listHeadersAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao.listHeadersAtHeight(height).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def findBestHeaderAtHeightApi(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao.findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(header.asJson)
      case None => NotFound
    }
  }

  def findBestHeaderAtHeightView(height: Int): Action[AnyContent] = heightCheck(height).async {
    historyDao.findBestHeaderAtHeight(height).map {
      case Some(header) => Ok(getHeaderView(header))
      case None => NotFound
    }
  }

  def listLastHeadersApi(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao.listLastHeaders(qty).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def listLastHeadersView(qty: Int): Action[AnyContent] = qtyCheck(qty).async {
    historyDao.listLastHeaders(qty).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def listHeadersByCountView(from: Int, count: Int): Action[AnyContent] = fromToCheck(from, count).async {
    historyDao.listHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

  def listHeadersByCountApi(from: Int, count: Int): Action[AnyContent] = fromToCheck(from, count).async {
    historyDao.listHeadersByCount(from, count).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def listHeadersByHeightRangeApi(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao.listHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(list.asJson)
    }
  }

  def listHeadersByHeightRangeView(from: Int, to: Int): Action[AnyContent] = fromToCheck(from, to).async {
    historyDao.listHeadersByHeightRange(from, to).map {
      case Nil => NotFound
      case list: List[Header] => Ok(getHeaderListView(list))
    }
  }

}
