package models

import javax.inject.Inject
import models.database.DBService
import models.database.HeadersQueryRepository._
import scala.concurrent.{ExecutionContext, Future}

class HistoryDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] = dBService.runAsync(findByIdQuery(id))

  def listHeadersAtHeight(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightQuery(height))

  def findBestHeaderAtHeight(height: Int): Future[Option[Header]] = dBService.runAsync(findBestByHeightQuery(height))

  def listLastHeaders(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByHeightQuery(qty))

  def listHeadersByHeightRange(from: Int, to: Int): Future[List[Header]] = dBService.runAsync(findByHeightRangeQuery(from, to))

  def listHeadersByCount(from: Int, count: Int): Future[List[Header]] = dBService.runAsync(findByCountQuery(from, count))

}