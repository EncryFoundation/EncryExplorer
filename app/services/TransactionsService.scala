package services

import javax.inject.Inject
import models.{Input, Output, Transaction, TransactionsDao}
import protocol.AccountLockedContract
import protocol.crypto.Base58Check
import scorex.crypto.encode.Base16
import scala.concurrent.{ExecutionContext, Future}

class TransactionsService @Inject()(transactionsDao: TransactionsDao)(implicit ec: ExecutionContext) {

  def findOutput(id: String): Future[Option[Output]] = Future.fromTry(Base16.decode(id))
    .flatMap(_ => transactionsDao.findOutput(id))

  def listOutputsByAddress(address: String, unspentOnly: Boolean = false): Future[List[Output]] =
    Future.fromTry(Base58Check.decode(address))
      .flatMap(_ => transactionsDao.listOutputsByContractHash(contractHashByAddress(address), unspentOnly))

  def findOutputsByTxId(id: String): Future[List[Output]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findOutputsByTxId(id))

  def findUnspentOutputsByTxId(id: String): Future[List[Output]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findUnspentOutputsByTxId(id))

  def findInput(id: String): Future[Option[Input]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findInput(id))

  def listInputs(txId: String): Future[List[Input]] =
    Future.fromTry(Base16.decode(txId))
      .flatMap(_ => transactionsDao.listInputs(txId))


  def findTransaction(id: String): Future[Option[Transaction]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findTransaction(id))

  def listTransactionsByBlockId(blockId: String): Future[List[Transaction]] =
    Future.fromTry(Base16.decode(blockId))
      .flatMap(_ => transactionsDao.listByBlockId(blockId))

  def listOutputsByBlockHeight(height: Int): Future[List[Output]] =
    if (height >= 0) transactionsDao.listOutputsByBlockHeight(height)
    else Future.failed(new IllegalArgumentException)

  def listOutputsByBlockHeightUnspent(height: Int): Future[List[Output]] =
    if (height >= 0) transactionsDao.listOutputsByBlockHeightUnspent(height)
    else Future.failed(new IllegalArgumentException)

  def findOutputByBlockId(id: String): Future[List[Output]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findOutputByBlockId(id))

  def findUnspentOutputByBlockId(id: String): Future[List[Output]] =
    Future.fromTry(Base16.decode(id))
      .flatMap(_ => transactionsDao.findUnspentOutputByBlockId(id))

  def findTransactionByBlockHeightRange(from: Int, to: Int): Future[List[Transaction]] =
    if (from >= 0 && to >= from) transactionsDao.findTransactionByBlockHeightRange(from, to)
    else Future.failed(new IllegalArgumentException)

  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex

}
