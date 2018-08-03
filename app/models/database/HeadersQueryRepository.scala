package models.database

import doobie._
import doobie.implicits._
import models.Header

object HeadersQueryRepository {

  def findByIdQuery(id: String): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE id = $id".query[Header].to[List].map(_.headOption)


  def findByHeightByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY height DESC".query[Header].to[List]

  def findByHeightByDateQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY ts DESC".query[Header].to[List]

  def findByHeightByTxsQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY tx_qty DESC".query[Header].to[List]

  def findByHeightByBlockSizeQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY block_size DESC".query[Header].to[List]

  def findByHeightByTxsSizeQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY txs_size DESC".query[Header].to[List]


  def findBestByHeightQuery(height: Int): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE height = $height AND best_chain = TRUE".query[Header].to[List].map(_.headOption)


  def findLastByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY height DESC LIMIT $height".query[Header].to[List]

  def findLastByDateQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY ts DESC LIMIT $height".query[Header].to[List]

  def findLastByTxsQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY tx_qty DESC LIMIT $height".query[Header].to[List]

  def findLastByBlockSizeQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY block_size DESC LIMIT $height".query[Header].to[List]

  def findLastByTxsSizeQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY txs_size DESC LIMIT $height".query[Header].to[List]


  def findByHeightRangeQuery(from: Int, to: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height BETWEEN $from AND $to".query[Header].to[List]

  def findByCountQuery(from: Int, count: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE (height BETWEEN $from AND ($from + $count - 1) AND best_chain = TRUE)".query[Header].to[List]

  def findHeadersByDateQuery(time: Long, count: Int): ConnectionIO[List[Header]] =
    sql"select * from headers where ts >= $time ORDER BY height DESC LIMIT $count".query[Header].to[List]
}

