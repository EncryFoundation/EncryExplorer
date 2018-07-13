package org.encryfoundation.explorer.http.api

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.{AuthorizationFailedRejection, Directive0, Directives}
import org.encryfoundation.explorer.settings.RESTApiSettings
import scorex.crypto.hash.Blake2b256

trait ApiDirectives extends Directives {
  val settings: RESTApiSettings
  val apiKeyHeaderName: String

  lazy val withCors: Directive0 =
    if (settings.corsAllowed) respondWithHeaders(RawHeader("Access-Control-Allow-Origin", "*")) else pass

  lazy val withAuth: Directive0 = optionalHeaderValueByName(apiKeyHeaderName).flatMap {
    case _ if settings.apiKeyHash.isEmpty => pass
    case None => reject(AuthorizationFailedRejection)
    case Some(key) =>
      if (settings.apiKeyHash.exists(_.toCharArray.sameElements(Blake2b256(key)))) pass
      else reject(AuthorizationFailedRejection)
  }
}