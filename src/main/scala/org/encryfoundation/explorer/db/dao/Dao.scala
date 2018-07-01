package org.encryfoundation.explorer.db.dao

import cats.implicits._
import doobie._
import doobie.implicits._

trait Dao {

  def perform[T](query: Query0[T], failureMsg: String): ConnectionIO[T] =
    query.option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(failureMsg)
      )
    }
}
