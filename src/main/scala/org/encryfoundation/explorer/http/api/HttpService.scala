package org.encryfoundation.explorer.http.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import org.encryfoundation.explorer.http.api.routes.ApiRoute
import org.encryfoundation.explorer.settings.RESTApiSettings

class HttpService(system: ActorSystem, routes: Seq[ApiRoute], settings: RESTApiSettings) {

  implicit val actorSystem: ActorSystem = system

  val compositeRoute: Route = routes.map(_.route).reduce(_ ~ _)
}
