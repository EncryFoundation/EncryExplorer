package org.encryfoundation.explorer

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import org.encryfoundation.explorer.http.api.routes.ApiRoute
import org.encryfoundation.explorer.settings.ExplorerAppSettings

import scala.concurrent.ExecutionContextExecutor

object ExplorerApp extends App {

  implicit val system: ActorSystem = ActorSystem("encry")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val settings: ExplorerAppSettings = ExplorerAppSettings.read

  val bindAddress: InetSocketAddress = settings.restApi.bindAddress

  val apiRoutes: Seq[ApiRoute] = Seq()

  val combinedRoute: Route = apiRoutes.map(_.route).reduce(_ ~ _)
  Http().bindAndHandle(combinedRoute, bindAddress.getAddress.getHostAddress, bindAddress.getPort)
}
