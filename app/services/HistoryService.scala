package services

import javax.inject.Inject
import models.{Header, HistoryDao}
import scorex.crypto.encode.Base16
import scala.concurrent.{ExecutionContext, Future}

class HistoryService @Inject()(historyDao: HistoryDao)(implicit ec: ExecutionContext) {

  def findHeadersByFromToDate(from: Long, to: Long): Future[List[Header]] =
    if (from >= 0 && to >= from) historyDao.findHeadersByFromToDate(from, to)
    else Future.failed(new IllegalArgumentException)
}
