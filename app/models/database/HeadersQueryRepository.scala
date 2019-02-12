package models.database

import doobie._
import doobie.implicits._
import models.Header

object HeadersQueryRepository {

  def findByIdQuery(id: String): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE id = $id".query[Header].to[List].map(_.headOption)

  def findByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height ORDER BY height DESC".query[Header].to[List]

  def findBestByHeightQuery(height: Int): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE height = $height AND best_chain = TRUE".query[Header].to[List].map(_.headOption)

  def findLastByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY height DESC LIMIT $height".query[Header].to[List]

  def findByHeightRangeQuery(from: Int, to: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height BETWEEN $from AND $to".query[Header].to[List]

  def findByCountQuery(from: Int, count: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE (height BETWEEN $from AND ($from + $count - 1) AND best_chain = TRUE) ORDER BY height DESC".query[Header].to[List]

  def findHeadersByFromToDateQuery(from: Long, to: Long): ConnectionIO[List[Header]] =
    sql"select * from headers where ts >= $from AND ts <= $to ORDER BY height DESC".query[Header].to[List]

  def findHeadersByDateQuery(time: Long, count: Int): ConnectionIO[List[Header]] =
    sql"select * from headers where ts >= $time ORDER BY height DESC LIMIT $count".query[Header].to[List]

  def findMaxHeightQuery: ConnectionIO[List[Header]] =
    sql"select * from headers where best_chain = true ORDER BY height DESC LIMIT 100".query[Header].to[List]

  def findHeaders(from: Int, to: Int): ConnectionIO[List[Header]] =
    sql"select * from headers where best_chain = true AND height >= $from AND height < $to ORDER BY height DESC".query[Header].to[List]
}