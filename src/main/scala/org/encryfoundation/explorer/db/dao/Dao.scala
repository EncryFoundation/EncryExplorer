package org.encryfoundation.explorer.db.dao

import cats.implicits._
import doobie._
import doobie.implicits._

trait Dao[M] {

  val table: String
  val columns: Seq[String]

  def columnsForQuery: String = columns.mkString(", ")

  implicit def liftQueryString(s: String): Fragment = Fragment.const(s)

  def perform(query: Query0[M], failureMsg: String): ConnectionIO[M] =
    query.option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(failureMsg)
      )
    }
}
