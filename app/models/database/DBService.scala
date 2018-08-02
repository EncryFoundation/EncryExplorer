package models.database

import cats.effect.IO
import doobie.free.connection.ConnectionIO
import doobie.hikari.HikariTransactor.newHikariTransactor
import doobie.implicits._
import javax.inject.Inject
import play.api.Configuration
import doobie.hikari.implicits._
import settings.ExplorerSettings
import scala.concurrent.Future

class DBService @Inject()(config: Configuration, settings: ExplorerSettings) {

  def runAsync[T](io: ConnectionIO[T]): Future[T] = (for {
    pool <- newHikariTransactor[IO](
      "org.postgresql.Driver",
      settings.postgres.host,
      settings.postgres.user,
      settings.postgres.password)
    _ <- pool.configure(ds => IO(ds.setMaximumPoolSize(5)))
    result <- io.transact(pool).guarantee(pool.shutdown)
  } yield result).unsafeToFuture()
}
