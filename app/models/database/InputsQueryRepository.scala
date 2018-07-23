package models.database

import doobie._
import doobie.implicits._
import models.Input

object InputsQueryRepository {

  def findInputQuery(id: String): ConnectionIO[Option[Input]] =
    sql"SELECT * FROM inputs WHERE id = $id".query[Input].to[List].map(_.headOption)

  def listInputsByTxIdQuery(txId: String): ConnectionIO[List[Input]] =
    sql"SELECT * FROM inputs WHERE tx_id = $txId".query[Input].to[List]
}
