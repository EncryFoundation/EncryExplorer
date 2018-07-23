package models.database

import doobie._
import doobie.implicits._
import models.Input

object InputsQueryRepository extends Dao[Input] {

  val table: String = "inputs"
  val columns: Seq[String] = Seq("id", "tx_id", "serialized_proofs")

  def findInputQuery(id: String): ConnectionIO[Option[Input]] =
    sql"SELECT * FROM inputs WHERE id = $id".query[Input].to[List].map(_.headOption)

  def listInputsByTxId(txId: String): ConnectionIO[List[Input]] =
    s"SELECT * FROM inputs WHERE tx_id = $txId".query[Input].to[List]

  def getById(id: String): ConnectionIO[Input] =
    perform(s"SELECT $columnsForQuery FROM $table WHERE id = '$id'".query[Input], s"Cannot find output with id = $id")

  def findByTxId(ch: String): ConnectionIO[List[Input]] =
    s"SELECT $columnsForQuery FROM $table WHERE tx_id = '$ch'".query[Input].to[List]

}
