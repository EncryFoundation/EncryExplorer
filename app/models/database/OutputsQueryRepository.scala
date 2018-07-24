package models.database

import doobie._
import doobie.implicits._
import models.Output

object OutputsQueryRepository {

  def findUnspentOutputsByTxId(id: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id = '$id' AND id NOT IN (SELECT id FROM inputs)".query[Output].to[List]

  def findOutputsByTxIdQuery(id: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id = '$id'".query[Output].to[List]

  def findOutputQuery(id: String): ConnectionIO[Option[Output]] =
    sql"SELECT * FROM outputs WHERE id = $id".query[Output].to[List].map(_.headOption)

  def listOutputsByContractHashQuery(contractHash: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE contract_hash = $contractHash".query[Output].to[List]

  def listUnspentOutputsByContractHashQuery(contractHash: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE contract_hash = $contractHash AND id NOT IN (SELECT id FROM inputs)".query[Output].to[List]

  def findUnspentOutputByBlockIdQuery(id: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id IN (SELECT id FROM transactions WHERE block_id = '$id')".query[Output].to[List]

  def findOutputByBlockIdQuery(id: String): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id IN (SELECT id FROM transactions WHERE block_id = '$id')".query[Output].to[List]

  def listByBlockHeightQuery(height: Int): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id IN (SELECT id FROM transactions WHERE block_id IN (SELECT id FROM headers WHERE height = $height))".query[Output].to[List]

  def listByBlockHeightQueryUnspentQuery(height: Int): ConnectionIO[List[Output]] =
    sql"SELECT * FROM outputs WHERE tx_id IN (SELECT id FROM transactions WHERE block_id IN (SELECT id FROM headers WHERE height = $height))".query[Output].to[List]
}

