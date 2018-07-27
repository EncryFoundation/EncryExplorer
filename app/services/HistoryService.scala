package services

import javax.inject.Inject
import models.{Header, HistoryDao}
import scorex.crypto.encode.Base16
import scala.concurrent.{ExecutionContext, Future}

class HistoryService @Inject()(historyDao: HistoryDao)(implicit ec: ExecutionContext) {

  def getHeader(id: String): Future[Option[Header]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => historyDao.findHeader(id))

  def getHeadersAtHeight(height: Int): Future[List[Header]] =
    if (height >= 0) historyDao.findHeadersAtHeight(height)
    else Future.failed(new IllegalArgumentException)

  def getBestHeaderAtHeight(height: Int): Future[Option[Header]] =
    if (height >= 0) historyDao.findBestHeadersAtHeight(height)
    else Future.failed(new IllegalArgumentException)

  def getLastHeaders(qty: Int): Future[List[Header]] =
    if (qty >= 0) historyDao.findLastHeaders(qty)
    else Future.failed(new IllegalArgumentException)

  def getHeadersByHeightRange(from: Int, to: Int): Future[List[Header]] =
    if (from >= 0 && to >= from) historyDao.findByRange(from, to)
    else Future.failed(new IllegalArgumentException)

  def findHeadersByCount(from: Int, count: Int): Future[List[Header]] =
    if (from >= 0 && count >= 0) historyDao.findByCount(from, count)
    else Future.failed(new IllegalArgumentException)

}
