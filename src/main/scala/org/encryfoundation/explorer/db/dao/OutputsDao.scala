package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Output

object OutputsDao extends Dao {

  import doobie._
  import doobie.implicits._
  import org.encryfoundation.explorer.db.tables.OutputsTable._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Output] = perform[Output](selectById(id), s"Cannot find output with id = $id")

  def findByContractHash(ch: String): ConnectionIO[List[Output]] = selectByContractHash(ch).to[List]

  def findUnspentByContractHash(ch: String): ConnectionIO[List[Output]] = selectUnspentByContractHash(ch).to[List]

  private def selectById(id: String): Query0[Output] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Output]

  private def selectByContractHash(ch: String): Query0[Output] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE contract_hash = $ch;").query[Output]

  private def selectUnspentByContractHash(ch: String): Query0[Output] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE contract_hash = $ch AND id NOT IN (SELECT id FROM inputs);").query[Output]
}
