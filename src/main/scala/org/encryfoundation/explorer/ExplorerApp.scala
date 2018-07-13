package org.encryfoundation.explorer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.ActorMaterializer
import cats.effect.IO
import doobie.hikari.HikariTransactor
import org.encryfoundation.explorer.db.services.{HistoryService, TransactionService}
import org.encryfoundation.explorer.http.api.routes.{ApiRoute, HistoryRoute, TransactionsApiRoute}
import org.encryfoundation.explorer.settings.ExplorerAppSettings
import org.encryfoundation.explorer.utils.Logging

import scala.concurrent.ExecutionContextExecutor

object ExplorerApp extends App with Logging {

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
  }.unsafeRunSync

  import akka.http.scaladsl.server._

  //  handleExceptions()


  val apiRoutes: Seq[ApiRoute] = Seq(
    HistoryRoute(HistoryService[IO](transactor, ec), settings.restApi),
    TransactionsApiRoute(TransactionService[IO](transactor, ec), settings.restApi)
  )
  logInfo("qq")
//  // logs just the request method and response status at info level
//  def requestMethodAndResponseStatusAsInfo(req: HttpRequest): RouteResult => Option[LogEntry] = {
//    case RouteResult.Complete(res) => Some(LogEntry(req.method.name + ": " + res.status, Logging.InfoLevel))
//    case _                         => None // no log entries for rejections
//  }
//  import org.encryfoundation.explorer.http.api.ApiDirectives

  val withLogger: Directive0 = {
    extractClientIP.flatMap { ip =>
    extractUri.flatMap { uri =>
      extractMethod.flatMap { method =>
        logInfo(s"User's IP: $ip    URI: $uri    method is: $method")
        pass
      }
    }}
    //    ++ extractMethod.flatMap{ method =>
    //      logInfo(s"Method is $method")
    //      pass
    //    }

  }

  val combinedRoute: Route = withLogger
  { apiRoutes.map(_.route).reduce(_ ~ _ )}


  //  val test: Route = withLogger {combinedRoute


  logWarn(s"$combinedRoute")


  Http().bindAndHandle(combinedRoute, settings.restApi.bindAddress.getAddress.getHostAddress, settings.restApi.bindAddress.getPort)


  logError(s"$combinedRoute")

}
