package models.database

import cats.effect.IO
import com.zaxxer.hikari.HikariDataSource
import doobie.free.connection.ConnectionIO
import doobie.hikari.HikariTransactor
import doobie.implicits._

import scala.concurrent.Future

class DBService {

  def runAsync[T](io: ConnectionIO[T]): Future[T] =
    (for {
      res <- io.transact(pgTransactor)
    } yield res)
      .unsafeToFuture()

  private val dataSource = new HikariDataSource
  dataSource.setJdbcUrl("jdbc:postgresql://172.16.10.55:5432/encry_explorer") //todo сюда значения из конфига
  dataSource.setUsername("db_admin")
  dataSource.setPassword("Yb2e-oii8-T3bo-pT8Z")
  dataSource.setMaximumPoolSize(5)

  private val pgTransactor: HikariTransactor[IO] = HikariTransactor[IO](dataSource)

}
