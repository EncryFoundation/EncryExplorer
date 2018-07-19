package models.services

import cats.Monad
import cats.effect.Async
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import models.dao.{InputsDao, OutputsDao, TransactionsDao}
import models.{Input, Output, Transaction}

import scala.concurrent.ExecutionContext

case class TransactionService[F[_]](tr: Transactor[F], ec: ExecutionContext)(implicit f: Monad[F], a: Async[F]) {

  def getOutput(id: String): F[Output] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.getById(id).transact[F](tr))

  def getOutputByContractHash(ch: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findByContractHash(ch).transact[F](tr))

  def getUnspentOutputByContractHash(ch: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findUnspentByContractHash(ch).transact[F](tr))

  def getOutputByTxId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findByTxId(id).transact[F](tr))

  def getUnspentOutputByTxId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findUnspentByTxId(id).transact[F](tr))

  def getInput(id: String): F[Input] = Async.shift[F](ec)
    .flatMap(_ => InputsDao.getById(id).transact[F](tr))

  def getInputByTxId(id: String): F[List[Input]] = Async.shift[F](ec)
    .flatMap(_ => InputsDao.findByTxId(id).transact[F](tr))

  def getTransaction(id: String): F[Transaction] = Async.shift[F](ec)
    .flatMap(_ => TransactionsDao.getById(id).transact[F](tr))

  def getTransactionByBlockId(id: String): F[List[Transaction]] = Async.shift[F](ec)
    .flatMap(_ => TransactionsDao.getByBlockId(id).transact[F](tr))

  def getOutputByBlockHeight(h: Int): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findByBlockHeight(h).transact[F](tr))

  def getUnspentOutputByBlockHeight(h: Int): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findUnspentByBlockHeight(h).transact[F](tr))

  def getOutputByBlockId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findByBlockId(id).transact[F](tr))

  def getUnspentOutputByBlockId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsDao.findUnspentByBlockId(id).transact[F](tr))

  def getTransactionByBlockHeightRange(from: Int, to: Int): F[List[Transaction]] = Async.shift[F](ec)
    .flatMap(_ => TransactionsDao.getByRange(from, to).transact[F](tr))

}
