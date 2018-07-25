package models

import javax.inject.Inject
import models.database.DBService
import models.database.HeadersQueryRepository._
import scala.concurrent.{ExecutionContext, Future}

class HistoryDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] = dBService.runAsync(findByIdQuery(id))

  def findHeadersAtHeight(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightQuery(height))

  def findBestHeadersAtHeight(height: Int): Future[Option[Header]] = dBService.runAsync(findBestByHeightQuery(height))

  def findLastHeaders(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByHeightQuery(qty))

  def findByRange(from: Int, to: Int): Future[List[Header]] = dBService.runAsync(findByHeightRangeQuery(from, to))

}