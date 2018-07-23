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
  //dataSource.setJdbcUrl(settings.postgres.host) todo сюда значения из конфига
  //dataSource.setUsername(settings.postgres.user)
  //dataSource.setPassword(settings.postgres.password)
  dataSource.setMaximumPoolSize(5)

  private val pgTransactor: HikariTransactor[IO] = HikariTransactor[IO](dataSource)

}
