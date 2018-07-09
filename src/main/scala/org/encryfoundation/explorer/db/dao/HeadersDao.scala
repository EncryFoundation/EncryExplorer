package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Header

object HeadersDao extends Dao[Header] {

  import doobie._
  import org.encryfoundation.explorer.db.tables.HeadersTable._

  val fieldsF: String = fields.mkString(", ")

  def getById(id: String): ConnectionIO[Header] = perform(selectById(id), s"Cannot find header with id = $id")

  def getByParentId(id: String): ConnectionIO[Header] = perform(selectByParentId(id), s"Cannot find header with id = $id")

  def getBestByHeight(height: Int): ConnectionIO[Header] = perform(selectBestAtHeight(height), s"Cannot find header with height = $height")

  def getByHeight(height: Int): ConnectionIO[List[Header]] = selectByHeight(height).to[List]

  def getLast(qty: Int): ConnectionIO[List[Header]] = selectLast(qty).to[List]

  private def selectById(id: String): Query0[Header] =
    s"SELECT $fieldsF FROM $name WHERE id = $id;".query[Header]

  private def selectByParentId(parentId: String): Query0[Header] =
    s"SELECT $fieldsF FROM $name WHERE parent_id = $parentId;".query[Header]

  private def selectByHeight(height: Int): Query0[Header] =
    s"SELECT $fieldsF FROM $name WHERE height = $height;".query[Header]

  private def selectBestAtHeight(height: Int): Query0[Header] =
    s"SELECT $fieldsF FROM $name WHERE height = $height AND best_chain = TRUE;".query[Header]

  private def selectLast(qty: Int): Query0[Header] =
    s"SELECT $fieldsF FROM $name WHERE best_chain = TRUE ORDER BY height DESC LIMIT $qty;".query[Header]
}
