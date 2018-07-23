package services

import cats.Monad
import cats.effect.Async
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import models.database.{InputsQueryRepository, OutputsQueryRepository, TransactionsQueryRepository}
import models.{Input, Output, Transaction}

import scala.concurrent.ExecutionContext

case class TransactionService[F[_]](tr: Transactor[F], ec: ExecutionContext)(implicit f: Monad[F], a: Async[F]) {

  def getOutputByTxId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findByTxId(id).transact[F](tr))

  def getUnspentOutputByTxId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findUnspentByTxId(id).transact[F](tr))

  def getInput(id: String): F[Input] = Async.shift[F](ec)
    .flatMap(_ => InputsQueryRepository.getById(id).transact[F](tr))

  def getInputByTxId(id: String): F[List[Input]] = Async.shift[F](ec)
    .flatMap(_ => InputsQueryRepository.findByTxId(id).transact[F](tr))

  def getTransactionByBlockId(id: String): F[List[Transaction]] = Async.shift[F](ec)
    .flatMap(_ => TransactionsQueryRepository.getByBlockId(id).transact[F](tr))

  def getOutputByBlockHeight(h: Int): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findByBlockHeight(h).transact[F](tr))

  def getUnspentOutputByBlockHeight(h: Int): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findUnspentByBlockHeight(h).transact[F](tr))

  def getOutputByBlockId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findByBlockId(id).transact[F](tr))

  def getUnspentOutputByBlockId(id: String): F[List[Output]] = Async.shift[F](ec)
    .flatMap(_ => OutputsQueryRepository.findUnspentByBlockId(id).transact[F](tr))

  def getTransactionByBlockHeightRange(from: Int, to: Int): F[List[Transaction]] = Async.shift[F](ec)
    .flatMap(_ => TransactionsQueryRepository.getByRange(from, to).transact[F](tr))

}
