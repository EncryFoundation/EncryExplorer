package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.syntax._
import org.encryfoundation.explorer.db.services.HistoryService
import org.encryfoundation.explorer.settings.RESTApiSettings

case class HistoryRoute(service: HistoryService[IO], settings: RESTApiSettings)
                       (implicit val context: ActorRefFactory) extends ApiRoute {

  override val route: Route = pathPrefix("history") {
    getHeaderR ~
      getHeadersAtHeightR ~
      getBestHeaderAtHeightR ~
      getLastHeadersR ~
      getHeadersByHeightRangeR
  }

  def getHeaderR: Route = (modifierId & pathPrefix("header") & get) { id =>
    toJsonResponse(service.getHeaderById(id).unsafeToFuture().map(_.asJson))
  }

  def getHeadersAtHeightR: Route = (pathPrefix("headersAt") & height & get) { h =>
    toJsonResponse(service.getHeadersAtHeight(h).unsafeToFuture().map(_.asJson))
  }

  def getBestHeaderAtHeightR: Route = (pathPrefix("bestHeaderAt") & height & get) { h =>
    toJsonResponse(service.getBestHeaderAtHeight(h).unsafeToFuture().map(_.asJson))
  }

  def getLastHeadersR: Route = (pathPrefix("lastHeaders") & qty & get) { q =>
    toJsonResponse(service.getLastHeaders(q).unsafeToFuture().map(_.asJson))
  }

  def getHeadersByHeightRangeR: Route = (pathPrefix("headers" / "range") & height & height & get ) { (from, to) =>
    toJsonResponse(service.getHeadersByHeightRange(from, to).unsafeToFuture().map(_.asJson))
  }

}
