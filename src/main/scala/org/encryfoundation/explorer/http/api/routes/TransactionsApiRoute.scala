package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.syntax._
import org.encryfoundation.explorer.db.services.TransactionService
import org.encryfoundation.explorer.protocol.AccountLockedContract
import org.encryfoundation.explorer.settings.RESTApiSettings

case class TransactionsApiRoute(service: TransactionService[IO], settings: RESTApiSettings)
                               (implicit val context: ActorRefFactory) extends ApiRoute {

  override val route: Route = pathPrefix("transaction") {
    getOutputR ~ getOutputsByAddressR
  }

  def getOutputR: Route = (modifierId & pathPrefix("output") & get) { id =>
    toJsonResponse(service.getOutput(id).unsafeToFuture().map(_.asJson))
  }

  def getOutputsByAddressR: Route = (accountAddress & pathPrefix("outputs") & get) { addr =>
    toJsonResponse(service.getOutputByContractHash(contractHashByAddress(addr)).unsafeToFuture().map(_.asJson))
  }

  private def contractHashByAddress(addr: String): String = AccountLockedContract(addr).contractHashHex
}
