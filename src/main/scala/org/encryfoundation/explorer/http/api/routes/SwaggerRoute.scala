package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import io.swagger.models.auth.BasicAuthDefinition
import org.encryfoundation.explorer.settings.RESTApiSettings

case class SwaggerRoute(settings: RESTApiSettings)(implicit val context: ActorRefFactory) extends SwaggerHttpService with ApiRoute {

  override val apiClasses = Set(classOf[HistoryRoute])
  override val host = "localhost:9052"
  override val info = Info(version = "0.1")
  override val externalDocs = None //Some(new ExternalDocs("Core Docs", "http://acme.com/docs"))
  override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")

  def route: Route = withCors(super.routes)

}