package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Header

object HeadersDao {

  import org.encryfoundation.explorer.db.tables.HeadersTable._

  import cats.implicits._
  import doobie._
  import doobie.implicits._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Header] = selectById(id)
    .option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(s"Cannot find header with id = $id")
      )
    }

  def getByParentId(id: String): ConnectionIO[Header] = selectByParentId(id)
    .option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(s"Cannot find header with id = $id")
      )
    }

  def getBestByHeight(height: Int): ConnectionIO[Header] = selectBestByHeight(height)
    .option.flatMap {
      case Some(h) => h.pure[ConnectionIO]
      case None => doobie.free.connection.raiseError(
        new NoSuchElementException(s"Cannot find header at height height = $height")
      )
    }

  def getByHeight(height: Int): ConnectionIO[List[Header]] = selectByHeight(height).to[List]

  private def selectById(id: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Header]

  private def selectByParentId(parentId: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE parent_id = $parentId").query[Header]

  private def selectByHeight(height: Int): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE height = $height").query[Header]

  private def selectBestByHeight(height: Int): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE height = $height AND best_chain = TRUE").query[Header]
}
