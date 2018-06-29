package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Header

object HeadersDao {

  import org.encryfoundation.explorer.db.tables.BlocksTable._

  import cats.implicits._
  import doobie._
  import doobie.implicits._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Header] = select(id).option.flatMap {
    case Some(h) => h.pure[ConnectionIO]
    case None => doobie.free.connection.raiseError(
      new NoSuchElementException(s"Cannot find header with id = $id")
    )
  }

  def getByParentId(id: String): ConnectionIO[Header] = selectByParentId(id).option.flatMap {
    case Some(h) => h.pure[ConnectionIO]
    case None => doobie.free.connection.raiseError(
      new NoSuchElementException(s"Cannot find header with id = $id")
    )
  }

  private def select(id: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Header]

  private def selectByParentId(parentId: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE parent_id = $parentId").query[Header]
}
