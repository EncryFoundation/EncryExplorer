package org.encryfoundation.explorer.db.dao

import doobie._
import org.encryfoundation.explorer.db.models.Input

object InputsDao extends Dao[Input] {

  val table: String = "inputs"
  val columns: Seq[String] = Seq("id", "tx_id", "serialized_proofs")

  def getById(id: String): ConnectionIO[Input] =
    perform(s"SELECT $columnsForQuery FROM $table WHERE id = '$id'".query[Input], s"Cannot find output with id = $id")

  def findByTxId(ch: String): ConnectionIO[List[Input]] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id = '$ch'".query[Input].to[List]

}
