package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Transaction

object TransactionsDao extends Dao[Transaction] {

  import doobie._
  import doobie.implicits._
  import org.encryfoundation.explorer.db.tables.TransactionsTable._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Transaction] = perform(selectById(id), s"Cannot find transaction with id = $id")

  def getByBlockId(id: String): ConnectionIO[List[Transaction]] = selectByBlockId(id).to[List]

  private def selectById(id: String): Query0[Transaction] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Transaction]

  private def selectByBlockId(id: String): Query0[Transaction] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE block_id = $id;").query[Transaction]
}
