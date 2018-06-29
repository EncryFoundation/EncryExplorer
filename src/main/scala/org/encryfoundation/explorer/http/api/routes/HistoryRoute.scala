package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.syntax._
import org.encryfoundation.explorer.db.services.HistoryService
import org.encryfoundation.explorer.settings.RESTApiSettings

case class HistoryRoute(service: HistoryService[IO], settings: RESTApiSettings)(implicit val context: ActorRefFactory) extends ApiRoute {

  override val route: Route = pathPrefix("history") {
    getHeaderR
  }

  def getHeaderR: Route = (modifierId & pathPrefix("header") & get) { id =>
    toJsonResponse(service.getHeader(id).unsafeToFuture().map(_.asJson))
  }
}
