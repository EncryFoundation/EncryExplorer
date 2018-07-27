package models.database

import doobie._
import doobie.implicits._
import models.Header

object HeadersQueryRepository {

  def findByIdQuery(id: String): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE id = $id".query[Header].to[List].map(_.headOption)

  def findByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height = $height".query[Header].to[List]

  def findBestByHeightQuery(height: Int): ConnectionIO[Option[Header]] =
    sql"SELECT * FROM headers WHERE height = $height AND best_chain = TRUE".query[Header].to[List].map(_.headOption)

  def findLastByHeightQuery(height: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE best_chain = TRUE ORDER BY height DESC LIMIT $height".query[Header].to[List]

  def findByHeightRangeQuery(from:Int, to: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE height BETWEEN $from AND $to".query[Header].to[List]

  def findByCountQuery(from:Int, count: Int): ConnectionIO[List[Header]] =
    sql"SELECT * FROM headers WHERE (height BETWEEN $from AND ($from + $count - 1) AND best_chain = TRUE)".query[Header].to[List]
}

