package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import io.swagger.models.ExternalDocs
import io.swagger.models.auth.{BasicAuthDefinition, SecuritySchemeDefinition}
import org.encryfoundation.explorer.db.models._
import org.encryfoundation.explorer.settings.RESTApiSettings

case class SwaggerRoute(settings: RESTApiSettings)(implicit val context: ActorRefFactory) extends SwaggerHttpService with ApiRoute {

  override val apiClasses: Set[Class[_]] = Set(classOf[HistoryRoute], classOf[TransactionsApiRoute])
  override val host: String = "localhost:9052"
  override val info: Info = Info(version = "0.1" )
  override val externalDocs: Option[ExternalDocs] = None
  override val securitySchemeDefinitions: Map[String, SecuritySchemeDefinition] = Map("basicAuth" -> new BasicAuthDefinition())
  override val unwantedDefinitions: Seq[String] = Seq("Function1", "Function1RequestContextFutureRouteResult")

  def route: Route = withCors(super.routes)

}