package models

import javax.inject.Inject
import models.database.DBService
import models.database.HeadersQueryRepository._
import scala.concurrent.{ExecutionContext, Future}

class HistoryDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] = dBService.runAsync(findById(id))

  def findHeadersAtHeight(height: Int): Future[List[Header]] = dBService.runAsync(findByHeight(height))

  def findBestHeadersAtHeight(height: Int): Future[Option[Header]] = dBService.runAsync(findBestByHeight(height))

  def findLastHeaders(height: Int): Future[List[Header]] = dBService.runAsync(findLastByHeight(height))

  def findByRange(from: Int, to: Int): Future[List[Header]] = dBService.runAsync(findByHeightRange(from, to))

}