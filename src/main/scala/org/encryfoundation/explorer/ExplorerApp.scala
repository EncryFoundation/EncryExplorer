package org.encryfoundation.explorer

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import cats.effect.IO
import doobie.hikari.HikariTransactor
import org.encryfoundation.explorer.db.services.{HistoryService, TransactionService}
import org.encryfoundation.explorer.http.api.routes.{ApiRoute, HistoryRoute, TransactionsApiRoute}
import org.encryfoundation.explorer.settings.ExplorerAppSettings

import scala.concurrent.ExecutionContextExecutor

object ExplorerApp extends App {

  implicit val system: ActorSystem = ActorSystem("encry")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val settings: ExplorerAppSettings = ExplorerAppSettings.read

  val transactor: HikariTransactor[IO] = HikariTransactor
    .newHikariTransactor[IO](
      driverClassName = "org.postgresql.Driver",
      url  = settings.postgres.host,
      user = settings.postgres.user,
      pass = settings.postgres.password
    ).map { ht => ht.configure(_ => IO(())); ht }
    .unsafeRunSync()

  val bindAddress: InetSocketAddress = settings.restApi.bindAddress

  val historyService: HistoryService[IO] = HistoryService[IO](transactor, ec)
  val transactionService: TransactionService[IO] = TransactionService[IO](transactor, ec)

  val apiRoutes: Seq[ApiRoute] = Seq(
    HistoryRoute(historyService, settings.restApi),
    TransactionsApiRoute(transactionService, settings.restApi)
  )

  val combinedRoute: Route = apiRoutes.map(_.route).reduce(_ ~ _)
  Http().bindAndHandle(combinedRoute, bindAddress.getAddress.getHostAddress, bindAddress.getPort)
}
