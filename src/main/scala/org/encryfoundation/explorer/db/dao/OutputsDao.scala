package org.encryfoundation.explorer.db.dao

import doobie._
import org.encryfoundation.explorer.db.models.Output

object OutputsDao extends Dao[Output] {

  val table: String = "outputs"
  val columns: Seq[String] = Seq("id", "tx_id", "monetary_value", "coin_id", "contract_hash", "data")

  def getById(id: String): ConnectionIO[Output] =
    perform(s"SELECT $columnsForQuery FROM $table WHERE id = '$id'".query[Output], s"Cannot find output with id = $id")

  def findByContractHash(ch: String): ConnectionIO[List[Output]] =
    s"SELECT $columnsForQuery FROM $table WHERE contract_hash = '$ch'".query[Output].to[List]

  def findUnspentByContractHash(ch: String): ConnectionIO[List[Output]] =
    s"SELECT $columnsForQuery FROM $table WHERE contract_hash = '$ch' AND id NOT IN (SELECT id FROM inputs)".query[Output].to[List]

  def findByTxId(ch: String): ConnectionIO[List[Output]] = selectByTxId(ch).to[List]

  def findUnspentByTxId(ch: String): ConnectionIO[List[Output]] = selectUnspentByTxId(ch).to[List]

  def findByBlockHeight(h: Int): ConnectionIO[List[Output]] = selectByBlockHeight(h).to[List]

  def findUnspentByBlockHeight(h: Int): ConnectionIO[List[Output]] = selectByBlockHeight(h).to[List]

  def findByBlockId(id: String): ConnectionIO[List[Output]] = selectByBlockId(id).to[List]

  def findUnspentByBlockId(id: String): ConnectionIO[List[Output]] = selectByBlockId(id).to[List]

  private def selectByTxId(id: String): Query0[Output] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id = '$id'".query[Output]

  private def selectUnspentByTxId(id: String): Query0[Output] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id = '$id' AND id NOT IN (SELECT id FROM inputs)".query[Output]

  private def selectByBlockHeight(height: Int): Query0[Output] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id IN (SELECT id FROM transactions WHERE block_id IN (SELECT id FROM headers WHERE height = $height))".query[Output]

  private def selectByBlockId(id: String): Query0[Output] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id IN (SELECT id FROM transactions WHERE block_id = '$id')".query[Output]

  private def selectUnspentByBlockId(id: String): Query0[Output] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id IN (SELECT id FROM transactions WHERE block_id = '$id') AND id NOT IN (SELECT id FROM inputs)".query[Output]

}
