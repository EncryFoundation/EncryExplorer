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
//  val postgresSettings: ExplorerAppSettings = ExplorerAppSettings.read
  dataSource.setJdbcUrl("")
  dataSource.setUsername("")
  dataSource.setPassword("")
  dataSource.setMaximumPoolSize(5)

  private val pgTransactor: HikariTransactor[IO] = HikariTransactor[IO](dataSource)

}
