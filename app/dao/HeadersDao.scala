package dao

import doobie._
import models.Header

object HeadersDao extends Dao[Header] {

  val table: String = "headers"
  val columns: Seq[String] = Seq(
    "id",
    "parent_id",
    "version",
    "height",
    "ad_proofs_root",
    "state_root",
    "transactions_root",
    "ts",
    "difficulty",
    "block_size",
    "equihash_solution",
    "ad_proofs",
    "tx_qty",
    "miner_address",
    "miner_reward",
    "fees_total",
    "txs_size",
    "best_chain"
  )

  def getById(id: String): ConnectionIO[Header] =
    perform(s"SELECT $columnsForQuery FROM $table WHERE id = '$id'".query[Header], s"Cannot find header with id = $id")

  def getByParentId(id: String): ConnectionIO[Header] = perform(selectByParentId(id), s"Cannot find header with id = $id")

  def getBestByHeight(height: Int): ConnectionIO[Header] = perform(selectBestAtHeight(height), s"Cannot find header with height = $height")

  def getByHeight(height: Int): ConnectionIO[List[Header]] = selectByHeight(height).to[List]

  def getLast(qty: Int): ConnectionIO[List[Header]] = selectLast(qty).to[List]

  def getByHeightRange(from: Int, to: Int): ConnectionIO[List[Header]] = selectByHeightRange(from, to).to[List]

  private def selectByParentId(parentId: String): Query0[Header] =
    s"SELECT $columnsForQuery FROM $table WHERE parent_id = '$parentId'".query[Header]

  private def selectByHeight(height: Int): Query0[Header] =
    s"SELECT $columnsForQuery FROM $table WHERE height = $height".query[Header]

  private def selectBestAtHeight(height: Int): Query0[Header] =
    s"SELECT $columnsForQuery FROM $table WHERE height = $height AND best_chain = TRUE".query[Header]

  private def selectLast(qty: Int): Query0[Header] =
    s"SELECT $columnsForQuery FROM $table WHERE best_chain = TRUE ORDER BY height DESC LIMIT $qty".query[Header]

  private def selectByHeightRange(from: Int, to: Int): Query0[Header] =
    s"SELECT $columnsForQuery FROM $table WHERE height BETWEEN $from AND $to".query[Header]

}

