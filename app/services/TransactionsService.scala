package services

import javax.inject.Inject
import models.{Input, Output, Transaction, TransactionsDao}
import protocol.AccountLockedContract

import scala.concurrent.{ExecutionContext, Future}

class TransactionsService @Inject()(transactionsDao: TransactionsDao)(implicit ec: ExecutionContext) {

  def findOutput(id: String): Future[Option[Output]] = {
    transactionsDao.findOutput(id)
  }

  def listOutputsByAddress(address: String, unspentOnly: Boolean = false): Future[List[Output]] = {
    transactionsDao.listOutputsByContractHash(contractHashByAddress(address), unspentOnly)
  }

  def findInput(id: String): Future[Option[Input]] = {
    transactionsDao.findInput(id)
  }

  def listInputs(txId: String): Future[List[Input]] = {
    transactionsDao.listInputs(txId)
  }

  def findOutputsByTxId(id: String): Future[List[Output]] = {
    transactionsDao.findOutputsByTxId(id)
  }

  def findUnspentOutputsByTxId(id: String): Future[List[Output]] = {
    transactionsDao.findUnspentOutputsByTxId(id)
  }

  def findTransaction(id: String): Future[Option[Transaction]] = {
    transactionsDao.findTransaction(id)
  }

  def listTransactionsByBlockId(blockId: String): Future[List[Transaction]] = {
    transactionsDao.listByBlockId(blockId)
  }

  def findOutputByBlockId(id: String): Future[List[Output]] = {
    transactionsDao.findOutputByBlockId(id)
  }

  def findUnspentOutputByBlockId(id: String): Future[List[Output]] = {
    transactionsDao.findUnspentOutputByBlockId(id)
  }

  def findTransactionByBlockHeightRange(from: Int, to: Int): Future[List[Transaction]] = {
    transactionsDao.findTransactionByBlockHeightRange(from, to)
  }

  def listOutputsByBlockHeight(height: Int): Future[List[Output]] = {
    transactionsDao.listOutputsByBlockHeight(height)
  }

  def listOutputsByBlockHeightUnspent(height: Int): Future[List[Output]] = {
    transactionsDao.listOutputsByBlockHeightUnspent(height)
  }

  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex

}
