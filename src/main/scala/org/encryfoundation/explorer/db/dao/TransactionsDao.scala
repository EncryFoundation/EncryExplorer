package org.encryfoundation.explorer.db.dao

import doobie._
import org.encryfoundation.explorer.db.models.Transaction

object TransactionsDao extends Dao[Transaction] {

  val name: String = "transactions"

  val fields: Seq[String] = Seq("id", "block_id", "is_coinbase", "ts")

  def getById(id: String): ConnectionIO[Transaction] = perform(selectById(id), s"Cannot find transaction with id = $id")

  def getByBlockId(id: String): ConnectionIO[List[Transaction]] = selectByBlockId(id).to[List]

  def getByRange(from: Int, to: Int): ConnectionIO[List[Transaction]] = selectByRange(from, to).to[List]

  private def selectById(id: String): Query0[Transaction] =
    s"SELECT $fieldsF FROM $name WHERE id = '$id'".query[Transaction]

  private def selectByBlockId(id: String): Query0[Transaction] =
    s"SELECT $fieldsF FROM $name WHERE block_id = '$id'".query[Transaction]

  private def selectByRange(from: Int, to: Int): Query0[Transaction] =
    s"SELECT $fieldsF FROM $name WHERE block_id IN (SELECT id FROM headers WHERE height BETWEEN $from AND $to)".query[Transaction]

}
