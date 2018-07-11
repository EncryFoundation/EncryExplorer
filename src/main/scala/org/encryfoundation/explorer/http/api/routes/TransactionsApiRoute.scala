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

  override val route: Route = pathPrefix("transactions") {
    getOutputR ~
      getOutputsByAddressR ~
      getUnspentOutputsByAddressR ~
      getOutputsByTxIdR ~
      getUnspentOutputsByTxIdR ~
      getInputR ~
      getInputsByTxIdR ~
      getUnspentOutputByBlockIdR ~
      getOutputByBlockIdR ~
      getUnspentOutputByBlockHeightR ~
      getOutputByBlockHeightR ~
      getTransactionR ~
      getTransactionsByBlockIdR
  }

  def getOutputR: Route = (pathPrefix("output") & modifierId & get) { id =>
    toJsonResponse(service.getOutput(id).unsafeToFuture().map(_.asJson))
  }

  def getOutputsByAddressR: Route = (address & pathPrefix("outputs") & get) { addr =>
    toJsonResponse(service.getOutputByContractHash(contractHashByAddress(addr)).unsafeToFuture().map(_.asJson))
  }

  def getUnspentOutputsByAddressR: Route = (address & pathPrefix("outputs" / "unspent") & get) { addr =>
    toJsonResponse(service.getUnspentOutputByContractHash(contractHashByAddress(addr)).unsafeToFuture().map(_.asJson))
  }

  def getOutputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("outputs") & get) { id =>
    toJsonResponse(service.getOutputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  def getUnspentOutputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("outputs" / "unspent") & get) { id =>
    toJsonResponse(service.getUnspentOutputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  def getInputR: Route = (pathPrefix("input") & modifierId & get) { id =>
    toJsonResponse(service.getInput(id).unsafeToFuture().map(_.asJson))
  }

  def getInputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("inputs") & get) { id =>
    toJsonResponse(service.getInputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  def getTransactionR: Route = (modifierId & get) { id =>
    toJsonResponse(service.getTransaction(id).unsafeToFuture().map(_.asJson))
  }

  def getTransactionsByBlockIdR: Route = (pathPrefix("block") & modifierId & get) { id =>
    toJsonResponse(service.getTransactionByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  def getOutputByBlockHeightR: Route = (pathPrefix("block" / "height") & height & pathPrefix("output") & get) { h =>
    toJsonResponse(service.getOutputByBlockHeight(h).unsafeToFuture().map(_.asJson))
  }

  def getUnspentOutputByBlockHeightR: Route = (pathPrefix("block" / "height") & height & pathPrefix("output" / "unspent") & get) { h =>
    toJsonResponse(service.getUnspentOutputByBlockHeight(h).unsafeToFuture().map(_.asJson))
  }

  def getOutputByBlockIdR: Route = (pathPrefix("block") & modifierId & pathPrefix("output") & get) { id =>
    toJsonResponse(service.getOutputByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  def getUnspentOutputByBlockIdR: Route = (pathPrefix("block") & modifierId & pathPrefix("output" / "unspent") & get) { id =>
    toJsonResponse(service.getUnspentOutputByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex
}
