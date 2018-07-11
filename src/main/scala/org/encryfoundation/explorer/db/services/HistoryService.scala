package org.encryfoundation.explorer.db.services

import cats.Monad
import cats.effect.Async
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.encryfoundation.explorer.db.dao.HeadersDao
import org.encryfoundation.explorer.db.models.Header

import scala.concurrent.ExecutionContext

case class HistoryService[F[_]](tr: Transactor[F], ec: ExecutionContext)(implicit f: Monad[F], a: Async[F]) {

  def getHeaderById(id: String): F[Header] = Async.shift[F](ec)
    .flatMap(_ => HeadersDao.getById(id).transact[F](tr))

  def getHeadersAtHeight(h: Int): F[List[Header]] = Async.shift[F](ec)
    .flatMap(_ => HeadersDao.getByHeight(h).transact[F](tr))

  def getBestHeaderAtHeight(h: Int): F[Header] = Async.shift[F](ec)
    .flatMap(_ => HeadersDao.getBestByHeight(h).transact[F](tr))

  def getLastHeaders(qty: Int): F[List[Header]] = Async.shift[F](ec)
    .flatMap(_ => HeadersDao.getLast(qty).transact[F](tr))

  def getHeadersByHeightRange(from: Int, to: Int): F[List[Header]] = Async.shift[F](ec)
    .flatMap(_ => HeadersDao.getByHeightRange(from, to).transact[F](tr))

}
