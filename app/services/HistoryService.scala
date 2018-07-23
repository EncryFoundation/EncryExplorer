package services

import javax.inject.Inject
import models.{Header, HistoryDao}
import scorex.crypto.encode.Base16
import scala.concurrent.{ExecutionContext, Future}

class HistoryService @Inject()(historyDao: HistoryDao)(implicit ec: ExecutionContext) {

  def getHeader(id: String): Future[Option[Header]] = {
    Future
      .fromTry(Base16.decode(id))
      .flatMap(_ => historyDao.findHeader(id))
  }
}
