package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Output

object OutputsDao extends Dao[Output] {

  import doobie._
  import org.encryfoundation.explorer.db.tables.OutputsTable._

  val fieldsF: String = fields.mkString(", ")

  def getById(id: String): ConnectionIO[Output] = perform(selectById(id), s"Cannot find output with id = $id")

  def findByContractHash(ch: String): ConnectionIO[List[Output]] = selectByContractHash(ch).to[List]

  def findUnspentByContractHash(ch: String): ConnectionIO[List[Output]] = selectUnspentByContractHash(ch).to[List]

  def findByTxId(ch: String): ConnectionIO[List[Output]] = selectByTxId(ch).to[List]

  def findUnspentByTxId(ch: String): ConnectionIO[List[Output]] = selectUnspentByTxId(ch).to[List]

  private def selectById(id: String): Query0[Output] =
    s"SELECT $fieldsF FROM $name WHERE id = $id;".query[Output]

  private def selectByContractHash(ch: String): Query0[Output] =
    s"SELECT $fieldsF FROM $name WHERE contract_hash = $ch;".query[Output]

  private def selectUnspentByContractHash(ch: String): Query0[Output] =
    s"SELECT $fieldsF FROM $name WHERE contract_hash = $ch AND id NOT IN (SELECT id FROM inputs);".query[Output]

  private def selectByTxId(id: String): Query0[Output] =
    s"SELECT $fieldsF FROM $name WHERE WHERE tx_id = $id;".query[Output]

  private def selectUnspentByTxId(id: String): Query0[Output] =
    s"SELECT $fieldsF FROM $name WHERE itx_id = $id AND id NOT IN (SELECT id FROM inputs);".query[Output]
}
