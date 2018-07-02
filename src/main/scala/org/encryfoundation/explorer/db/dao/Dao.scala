package org.encryfoundation.explorer.db.dao

import cats.implicits._
import doobie._
import doobie.implicits._

trait Dao[M] {

  def perform(query: Query0[M], failureMsg: String): ConnectionIO[M] =
    query.option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(failureMsg)
      )
    }
}
