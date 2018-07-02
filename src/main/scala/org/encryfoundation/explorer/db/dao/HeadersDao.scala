package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Header

object HeadersDao extends Dao[Header] {

  import doobie._
  import doobie.implicits._
  import org.encryfoundation.explorer.db.tables.HeadersTable._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Header] = perform(selectById(id), s"Cannot find header with id = $id")

  def getByParentId(id: String): ConnectionIO[Header] = perform(selectByParentId(id), s"Cannot find header with id = $id")

  def getBestByHeight(height: Int): ConnectionIO[Header] = perform(selectBestAtHeight(height), s"Cannot find header with height = $height")

  def getByHeight(height: Int): ConnectionIO[List[Header]] = selectByHeight(height).to[List]

  def getLast(qty: Int): ConnectionIO[List[Header]] = selectLast(qty).to[List]

  private def selectById(id: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Header]

  private def selectByParentId(parentId: String): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE parent_id = $parentId").query[Header]

  private def selectByHeight(height: Int): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE height = $height").query[Header]

  private def selectBestAtHeight(height: Int): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE height = $height AND best_chain = TRUE").query[Header]

  private def selectLast(qty: Int): Query0[Header] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE best_chain = TRUE ORDER BY height DESC LIMIT $qty").query[Header]
}
