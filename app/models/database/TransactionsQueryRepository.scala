package models.database

import doobie._
import doobie.implicits._
import models.Transaction

object TransactionsQueryRepository {

  def findTransactionQuery(id: String): ConnectionIO[Option[Transaction]] =
    sql"SELECT * FROM transactions WHERE id = $id".query[Transaction].to[List].map(_.headOption)

  def listByBlockIdQuery(blockId: String): ConnectionIO[List[Transaction]] =
    sql"SELECT * FROM transactions WHERE block_id = $blockId".query[Transaction].to[List]
}
