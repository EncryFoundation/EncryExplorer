package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.syntax._
import io.swagger.annotations._
import javax.ws.rs.{Path, Produces}
import org.encryfoundation.explorer.db.models._
import org.encryfoundation.explorer.db.services.TransactionService
import org.encryfoundation.explorer.protocol.AccountLockedContract
import org.encryfoundation.explorer.settings.RESTApiSettings

@Path("/transactions")
@Api(value = "transactions", tags = Array("transactions"))
@Produces(Array("application/json"))
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
      getTransactionsByBlockIdR ~
      getTransactionByBlockHeightRangeR
  }

  @Path("/output/{id}")
  @ApiOperation(
    value = "Finds a output for the given ID",
    notes = "Returns a output",
    httpMethod = "GET",
    response = classOf[Output])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "ID of output", required = true, dataType = "string", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Output not found"),
    new ApiResponse(code = 200, message = "Output found")))
  def getOutputR: Route = (pathPrefix("output") & modifierId & get) { id =>
    toJsonResponse(service.getOutput(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/{address}/outputs")
  @ApiOperation(
    value = "Finds outputs for the given wallet address",
    notes = "Returns outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "address", value = "Wallet address", required = true, dataType = "string", paramType = "path")
  ))
  def getOutputsByAddressR: Route = (address & pathPrefix("outputs") & get) { addr =>
    toJsonResponse(service.getOutputByContractHash(contractHashByAddress(addr)).unsafeToFuture().map(_.asJson))
  }

  @Path("/{address}/outputs/unspent")
  @ApiOperation(
    value = "Finds unspent outputs for the given wallet address",
    notes = "Returns unspent outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "address", value = "Wallet address", required = true, dataType = "string", paramType = "path")
  ))
  def getUnspentOutputsByAddressR: Route = (address & pathPrefix("outputs" / "unspent") & get) { addr =>
    toJsonResponse(service.getUnspentOutputByContractHash(contractHashByAddress(addr)).unsafeToFuture().map(_.asJson))
  }

  @Path("/tx/{id}/outputs")
  @ApiOperation(
    value = "Finds outputs for the given transaction ID",
    notes = "Returns outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Transaction ID", required = true, dataType = "string", paramType = "path")
  ))
  def getOutputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("outputs") & get) { id =>
    toJsonResponse(service.getOutputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/tx/{id}/outputs/unspent")
  @ApiOperation(
    value = "Finds unspent outputs for the given transaction ID",
    notes = "Returns unspent outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Transaction ID", required = true, dataType = "string", paramType = "path")
  ))
  def getUnspentOutputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("outputs" / "unspent") & get) { id =>
    toJsonResponse(service.getUnspentOutputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/input/{id}")
  @ApiOperation(
    value = "Finds an input for the given ID",
    notes = "Returns an input",
    httpMethod = "GET",
    response = classOf[Input],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Input ID", required = true, dataType = "string", paramType = "path")
  ))
  def getInputR: Route = (pathPrefix("input") & modifierId & get) { id =>
    toJsonResponse(service.getInput(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/tx/{id}/inputs")
  @ApiOperation(
    value = "Finds inputs for the given transaction ID",
    notes = "Returns an input",
    httpMethod = "GET",
    response = classOf[Input],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Transaction ID", required = true, dataType = "string", paramType = "path")
  ))
  def getInputsByTxIdR: Route = (pathPrefix("tx") & modifierId & pathPrefix("inputs") & get) { id =>
    toJsonResponse(service.getInputByTxId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/{id}")
  @ApiOperation(
    value = "Finds a transaction for the given ID",
    notes = "Returns a transaction",
    httpMethod = "GET",
    response = classOf[Transaction])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Transaction ID", required = true, dataType = "string", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Transaction not found"),
    new ApiResponse(code = 200, message = "Transaction found")))
  def getTransactionR: Route = (modifierId & get) { id =>
    toJsonResponse(service.getTransaction(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/block/{id}")
  @ApiOperation(
    value = "Finds transactions for the given block ID",
    notes = "Returns transactions",
    httpMethod = "GET",
    response = classOf[Transaction],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Block ID", required = true, dataType = "string", paramType = "path")
  ))
  def getTransactionsByBlockIdR: Route = (pathPrefix("block") & modifierId & get) { id =>
    toJsonResponse(service.getTransactionByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/block/height/{height}/outputs")
  @ApiOperation(
    value = "Finds outputs for the blocks with the given height",
    notes = "Returns outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Block height", required = true, dataType = "string", paramType = "path")
  ))
  def getOutputByBlockHeightR: Route = (pathPrefix("block" / "height") & height & pathPrefix("outputs") & get) { h =>
    toJsonResponse(service.getOutputByBlockHeight(h).unsafeToFuture().map(_.asJson))
  }

  @Path("/block/height/{height}/outputs/unspent")
  @ApiOperation(
    value = "Finds unspent outputs for the blocks with the given height",
    notes = "Returns unspent outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Block height", required = true, dataType = "string", paramType = "path")
  ))
  def getUnspentOutputByBlockHeightR: Route = (pathPrefix("block" / "height") & height & pathPrefix("outputs" / "unspent") & get) { h =>
    toJsonResponse(service.getUnspentOutputByBlockHeight(h).unsafeToFuture().map(_.asJson))
  }

  @Path("/block/{id}/outputs")
  @ApiOperation(
    value = "Finds outputs for the blocks with the given height",
    notes = "Returns outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Block ID", required = true, dataType = "string", paramType = "path")
  ))
  def getOutputByBlockIdR: Route = (pathPrefix("block") & modifierId & pathPrefix("outputs") & get) { id =>
    toJsonResponse(service.getOutputByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/block/{id}/outputs/unspent")
  @ApiOperation(
    value = "Finds unspent outputs for the blocks with the given height",
    notes = "Returns unspent outputs",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "Block ID", required = true, dataType = "string", paramType = "path")
  ))
  def getUnspentOutputByBlockIdR: Route = (pathPrefix("block") & modifierId & pathPrefix("outputs" / "unspent") & get) { id =>
    toJsonResponse(service.getUnspentOutputByBlockId(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/tx/range/{from}/{to}")
  @ApiOperation(
    value = "Finds transactions for blocks with height in the given range inclusively",
    notes = "Returns range of transactions inclusively",
    httpMethod = "GET",
    response = classOf[Output],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "from", value = "From block height", required = true, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "to", value = "To block height", required = true, dataType = "string", paramType = "path")
  ))
  def getTransactionByBlockHeightRangeR: Route = (pathPrefix("tx" / "range") & height & height & get) { (from, to) =>
    toJsonResponse(service.getTransactionByBlockHeightRange(from, to).unsafeToFuture().map(_.asJson))
  }

  private def contractHashByAddress(address: String): String = AccountLockedContract(address).contractHashHex
}
