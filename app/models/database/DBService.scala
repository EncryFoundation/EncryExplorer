package models.database

import cats.effect.IO
import com.zaxxer.hikari.HikariDataSource
import doobie.free.connection.ConnectionIO
import doobie.hikari.HikariTransactor
import doobie.implicits._
import javax.inject.Inject
import play.api.Configuration
import scala.concurrent.Future

class DBService @Inject()(config: Configuration) {

  def runAsync[T](io: ConnectionIO[T]): Future[T] =
    io.transact(pgTransactor).unsafeToFuture()

  private val dataSource = new HikariDataSource
  dataSource.setJdbcUrl(config.get[String]("encry.postgres.host"))
  dataSource.setUsername(config.get[String]("encry.postgres.username"))
  dataSource.setPassword(config.get[String]("encry.postgres.password"))
  dataSource.setMaximumPoolSize(5)

  private val pgTransactor: HikariTransactor[IO] = HikariTransactor[IO](dataSource)

}
