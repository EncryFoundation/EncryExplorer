package org.encryfoundation.explorer.db.services

import cats.Monad
import cats.effect.Async
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.encryfoundation.explorer.db.dao.OutputsDao
import org.encryfoundation.explorer.db.models.Output

import scala.concurrent.ExecutionContext

case class TransactionService[F[_]](tr: Transactor[F], ec: ExecutionContext)(implicit f: Monad[F], a: Async[F]) {

  def getOutput(id: String): F[Output] = for {
    _   <- Async.shift[F](ec)
    res <- OutputsDao.getById(id).transact[F](tr)
  } yield res

  def getOutputByContractHash(ch: String): F[List[Output]] = for {
    _   <- Async.shift[F](ec)
    res <- OutputsDao.findByContractHash(ch).transact[F](tr)
  } yield res
}
