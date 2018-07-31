package models.database

import cats.effect.IO
import doobie.free.connection.ConnectionIO
import doobie.hikari.HikariTransactor.newHikariTransactor
import doobie.implicits._
import javax.inject.Inject
import play.api.Configuration
import doobie.hikari.implicits._
import scala.concurrent.Future

class DBService @Inject()(config: Configuration) {

  def runAsync[T](io: ConnectionIO[T]): Future[T] = (for {
    pool <- newHikariTransactor[IO](
      "org.postgresql.Driver",
      host,
      user,
      pass)
    _ <- pool.configure(ds => IO(ds.setMaximumPoolSize(5)))
    result <- io.transact(pool).guarantee(pool.shutdown)
  } yield result).unsafeToFuture()

  private val host: String = config.get[String]("encry.postgres.host")
  private val user: String = config.get[String]("encry.postgres.username")
  private val pass: String = config.get[String]("encry.postgres.password")

}
