package models.database

import doobie._
import models.Transaction

object TransactionsDao extends Dao[Transaction] {

  val table: String = "transactions"
  val columns: Seq[String] = Seq("id", "block_id", "is_coinbase", "ts")

  def getById(id: String): ConnectionIO[Transaction] = perform(selectById(id), s"Cannot find transaction with id = $id")

  def getByBlockId(id: String): ConnectionIO[List[Transaction]] = selectByBlockId(id).to[List]

  def getByRange(from: Int, to: Int): ConnectionIO[List[Transaction]] = selectByRange(from, to).to[List]

  private def selectById(id: String): Query0[Transaction] =
    s"SELECT $columnsForQuery FROM $table WHERE id = '$id'".query[Transaction]

  private def selectByBlockId(id: String): Query0[Transaction] =
    s"SELECT $columnsForQuery FROM $table WHERE block_id = '$id'".query[Transaction]

  private def selectByRange(from: Int, to: Int): Query0[Transaction] =
    s"SELECT $columnsForQuery FROM $table WHERE block_id IN (SELECT id FROM headers WHERE height BETWEEN $from AND $to)".query[Transaction]
}
