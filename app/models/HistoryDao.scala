package models

import javax.inject.Inject
import models.database.DBService
import models.database.HeadersQueryRepository.findById
import scala.concurrent.{ExecutionContext, Future}

class HistoryDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] = dBService.runAsync(findById(id))
}