package models

import javax.inject.Inject
import models.database.DBService
import models.database.InputsQueryRepository._
import models.database.OutputsQueryRepository._
import models.database.TransactionsQueryRepository._
import scala.concurrent.{ExecutionContext, Future}

class TransactionsDao @Inject()(dBService: DBService)(implicit ec: ExecutionContext) {

  def findOutput(id: String): Future[Option[Output]] = dBService.runAsync(findOutputQuery(id))

  def listOutputsByContractHash(contractHash: String, unspentOnly: Boolean): Future[List[Output]] =
    if (unspentOnly) dBService.runAsync(listUnspentOutputsByContractHashQuery(contractHash))
    else dBService.runAsync(listOutputsByContractHashQuery(contractHash))

  def findInput(id: String): Future[Option[Input]] = dBService.runAsync(findInputQuery(id))

  def listInputs(txId: String): Future[List[Input]] = dBService.runAsync(listInputsByTxIdQuery(txId))

  def findOutputsByTxId(id: String): Future[List[Output]] = dBService.runAsync(findOutputsByTxIdQuery(id))

  def findUnspentOutputsByTxId(id: String): Future[List[Output]] = dBService.runAsync(findOutputsByTxIdQuery(id))

  def findTransaction(id: String): Future[Option[Transaction]] = dBService.runAsync(findTransactionQuery(id))

  def findOutputByBlockId(id: String): Future[List[Output]] = dBService.runAsync(findOutputByBlockIdQuery(id))

  def findUnspentOutputByBlockId(id: String): Future[List[Output]] = dBService.runAsync(findUnspentOutputByBlockIdQuery(id))

  def findTransactionByBlockHeightRange(from: Int, to: Int): Future[List[Transaction]] = dBService.runAsync(findTransactionByBlockHeightRangeQuery(from, to))

  def listByBlockId(blockId: String): Future[List[Transaction]] = dBService.runAsync(listByBlockIdQuery(blockId))

  def listOutputsByBlockHeight(height: Int): Future[List[Output]] = dBService.runAsync(listByBlockHeightQuery(height))

  def listOutputsByBlockHeightUnspent(height: Int): Future[List[Output]] = dBService.runAsync(listByBlockHeightQueryUnspentQuery(height))
}