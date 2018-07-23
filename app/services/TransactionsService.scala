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

  def findTransaction(id: String): Future[Option[Transaction]] = {
    transactionsDao.findTransaction(id)
  }



  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex

}
