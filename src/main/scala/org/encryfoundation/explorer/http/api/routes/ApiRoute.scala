package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.{Directive, Directive1, Route}
import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers
import akka.util.Timeout
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Json
import org.encryfoundation.explorer.Address
import org.encryfoundation.explorer.http.api.ApiDirectives
import org.encryfoundation.explorer.protocol.crypto.Base58Check
import scorex.crypto.authds.ADKey
import scorex.crypto.encode.Base16

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Success

trait ApiRoute extends ApiDirectives with FailFastCirceSupport with PredefinedFromEntityUnmarshallers {

  def context: ActorRefFactory

  def route: Route

  override val apiKeyHeaderName: String = "api_key"

  implicit lazy val timeout: Timeout = Timeout(settings.timeout)

  implicit val ec: ExecutionContextExecutor = context.dispatcher

  protected def toJsonResponse(js: Json): Route = {
    val resp = complete(HttpEntity(ContentTypes.`application/json`, js.spaces2))
    withCors(resp)
  }

  protected def toJsonResponse(fn: Future[Json]): Route = onSuccess(fn) { toJsonResponse }

  protected def toJsonOptionalResponse(fn: Future[Option[Json]]): Route = {
    onSuccess(fn) {
      case Some(v) => toJsonResponse(v)
      case None => withCors(complete(StatusCodes.NotFound))
    }
  }

  val paging: Directive[(Int, Int)] = parameters("offset".as[Int] ? 0, "limit".as[Int] ? 50)

  val modifierId: Directive1[String] = pathPrefix(Segment).flatMap { h =>
    Base16.decode(h) match {
      case Success(_) => provide(h)
      case _ => reject
    }
  }

  val height: Directive1[Int] = pathPrefix(Segment).flatMap { hs =>
    val h: Int = hs.toInt
    if (h > 0) provide(h) else reject
  }

  val accountAddress: Directive1[Address] = pathPrefix(Segment).flatMap { addr =>
    Base58Check.decode(addr) match {
      case Success(_) => provide(Address @@ addr)
      case _ => reject
    }
  }

  val boxId: Directive1[ADKey] = pathPrefix(Segment).flatMap { key =>
    Base16.decode(key) match {
      case Success(k) => provide(ADKey @@ k)
      case _ => reject
    }
  }

  implicit class OkJsonResp(fn: Future[Json]) {
    def okJson(): Route = toJsonResponse(fn)
  }

  implicit class OkJsonOptResp(fn: Future[Option[Json]]) {
    def okJson(): Route = toJsonOptionalResponse(fn)
  }
}
