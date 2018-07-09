package org.encryfoundation.explorer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
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

  implicit def apiExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case _: Exception =>
        extractUri { _ =>
          complete(HttpResponse(InternalServerError, entity = "Internal server error"))
        }
    }

  val settings: ExplorerAppSettings = ExplorerAppSettings.read

  val transactor: HikariTransactor[IO] = HikariTransactor.newHikariTransactor[IO](
    driverClassName = "org.postgresql.Driver",
    url = settings.postgres.host,
    user = settings.postgres.user,
    pass = settings.postgres.password).map { ht =>
    ht.configure(c => IO {
      c.setAutoCommit(false)
    })
    ht
  }.unsafeRunSync()

  val apiRoutes: Seq[ApiRoute] = Seq(
    HistoryRoute(HistoryService[IO](transactor, ec), settings.restApi),
    TransactionsApiRoute(TransactionService[IO](transactor, ec), settings.restApi)
  )

  val combinedRoute: Route = apiRoutes.map(_.route).reduce(_ ~ _)
  Http().bindAndHandle(combinedRoute, settings.restApi.bindAddress.getAddress.getHostAddress, settings.restApi.bindAddress.getPort)
}
