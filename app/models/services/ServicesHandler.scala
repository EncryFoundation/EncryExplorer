package models.services

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.IO
import com.google.inject.Provider
import doobie.hikari.HikariTransactor
import javax.inject.Inject

import scala.concurrent.ExecutionContext

class ServicesHandler @Inject()(system: ActorSystem, materializerProvider: MaterializerProvider)(implicit ec: ExecutionContext) {

  val transactor: HikariTransactor[IO] = HikariTransactor.newHikariTransactor[IO](
    driverClassName = "org.postgresql.Driver",
    url = "jdbc:postgresql://172.16.10.55:5432/encry_explorer",
    user = "db_admin",
    pass = "Yb2e-oii8-T3bo-pT8Z").map { ht =>
    ht.configure(c => IO {
      c.setAutoCommit(false)
    })
    ht
  }.unsafeRunSync()

  val hs: HistoryService[IO] = new HistoryService[IO](transactor, ec)
  val ts: TransactionService[IO] = new TransactionService[IO](transactor, ec)

}

class MaterializerProvider @Inject()(actorSystem: ActorSystem) extends Provider[Materializer] {
  lazy val get: Materializer = ActorMaterializer()(actorSystem)
}
