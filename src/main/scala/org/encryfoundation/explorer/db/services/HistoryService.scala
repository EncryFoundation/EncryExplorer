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

  def getHeaderById(id: String): F[Header] = for {
    _   <- Async.shift[F](ec)
    res <- HeadersDao.getById(id).transact[F](tr)
  } yield res

  def getHeadersAtHeight(h: Int): F[List[Header]] = for {
    _   <- Async.shift[F](ec)
    res <- HeadersDao.getByHeight(h).transact[F](tr)
  } yield res

  def getBestHeaderAtHeight(h: Int): F[Header] = for {
    _   <- Async.shift[F](ec)
    res <- HeadersDao.getBestByHeight(h).transact[F](tr)
  } yield res

  def getLastHeaders(gty: Int): F[List[Header]] = for {
    _   <- Async.shift[F](ec)
    res <- HeadersDao.getLast(gty).transact[F](tr)
  } yield res
}
