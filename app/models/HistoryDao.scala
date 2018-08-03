package models

import javax.inject.Inject
import models.database.DBService
import models.database.HeadersQueryRepository._
import scala.concurrent.{ExecutionContext, Future}

class HistoryDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findHeader(id: String): Future[Option[Header]] = dBService.runAsync(findByIdQuery(id))


  def listHeadersAtHeightByHeight(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightByHeightQuery(height))

  def listHeadersAtHeightByDate(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightByDateQuery(height))

  def listHeadersAtHeightByTxs(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightByTxsQuery(height))

  def listHeadersAtHeightByBlockSize(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightByBlockSizeQuery(height))

  def listHeadersAtHeightByTxsSize(height: Int): Future[List[Header]] = dBService.runAsync(findByHeightByTxsSizeQuery(height))

  def findBestHeaderAtHeight(height: Int): Future[Option[Header]] = dBService.runAsync(findBestByHeightQuery(height))

  def listLastHeadersByHeight(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByHeightQuery(qty))

  def listLastHeadersByDate(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByDateQuery(qty))

  def listLastHeadersByTxs(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByTxsQuery(qty))

  def listLastHeadersByBlockSize(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByBlockSizeQuery(qty))

  def listLastHeadersByTxsSize(qty: Int): Future[List[Header]] = dBService.runAsync(findLastByTxsSizeQuery(qty))


  def listHeadersByHeightRange(from: Int, to: Int): Future[List[Header]] = dBService.runAsync(findByHeightRangeQuery(from, to))

  def listHeadersByCount(from: Int, count: Int): Future[List[Header]] = dBService.runAsync(findByCountQuery(from, count))

  def findHeadersByDate(time: Long, count: Int): Future[List[Header]] = dBService.runAsync(findHeadersByDateQuery(time, count))
}