package services

import javax.inject.Inject
import models.{Header, HistoryDao}
import scorex.crypto.encode.Base16
import scala.concurrent.{ExecutionContext, Future}

class HistoryService @Inject()(historyDao: HistoryDao)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => historyDao.findHeader(id))

  def listHeadersAtHeight(height: Int): Future[List[Header]] =
    if (height >= 0) historyDao.findHeadersAtHeight(height)
    else Future.failed(new IllegalArgumentException)

  def findBestHeaderAtHeight(height: Int): Future[Option[Header]] =
    if (height >= 0) historyDao.findBestHeadersAtHeight(height)
    else Future.failed(new IllegalArgumentException)

  def listLastHeaders(qty: Int): Future[List[Header]] =
    if (qty >= 0) historyDao.findLastHeaders(qty)
    else Future.failed(new IllegalArgumentException)

  def listHeadersByHeightRange(from: Int, to: Int): Future[List[Header]] =
    if (from >= 0 && to >= from) historyDao.findByRange(from, to)
    else Future.failed(new IllegalArgumentException)

  def findHeadersByCount(from: Int, count: Int): Future[List[Header]] =
    if (from >= 0 && count >= 0) historyDao.findByCount(from, count)
    else Future.failed(new IllegalArgumentException)

  def findHeadersByDate(time: Long, count: Int): Future[List[Header]] =
    if (time >= 0 && count > 0) historyDao.findHeadersByDate(time, count)
    else Future.failed(new IllegalArgumentException)

}
