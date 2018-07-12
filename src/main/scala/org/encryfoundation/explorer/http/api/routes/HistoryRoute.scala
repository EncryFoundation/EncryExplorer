package org.encryfoundation.explorer.http.api.routes

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.syntax._
import org.encryfoundation.explorer.db.services.HistoryService
import org.encryfoundation.explorer.settings.RESTApiSettings
import javax.ws.rs.{Path, Produces}
import io.swagger.annotations._
import org.encryfoundation.explorer.db.models.Header

@Path("/history")
@Api(value = "history", tags = Array("history"))
@Produces(Array("application/json"))
case class HistoryRoute(service: HistoryService[IO], settings: RESTApiSettings)
                       (implicit val context: ActorRefFactory) extends ApiRoute {

  override val route: Route = pathPrefix("history") {
    getHeaderR ~
      getHeadersAtHeightR ~
      getBestHeaderAtHeightR ~
      getLastHeadersR ~
      getHeadersByHeightRangeR
  }

  @Path("/{id}/header")
  @ApiOperation(
    value = "Finds a block header for the given ID",
    notes = "Returns a header",
    httpMethod = "GET",
    response = classOf[Header])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "id", value = "ID of header", required = true, dataType = "string", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Header not found"),
    new ApiResponse(code = 200, message = "Header found")))
  def getHeaderR: Route = (modifierId & pathPrefix("header") & get) { id =>
    toJsonResponse(service.getHeaderById(id).unsafeToFuture().map(_.asJson))
  }

  @Path("/headersAt/{height}")
  @ApiOperation(
    value = "Finds block headers for given block height",
    notes = "Returns headers",
    httpMethod = "GET",
    response = classOf[Header],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "height", value = "Height of block", required = true, dataType = "integer", paramType = "path")
  ))
  def getHeadersAtHeightR: Route = (pathPrefix("headersAt") & height & get) { h =>
    toJsonResponse(service.getHeadersAtHeight(h).unsafeToFuture().map(_.asJson))
  }

  @Path("/bestHeaderAt/{height}")
  @ApiOperation(
    value = "Finds block headers for given block height in the best chain",
    notes = "Returns headers",
    httpMethod = "GET",
    response = classOf[Header],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "height", value = "Height of block", required = true, dataType = "integer", paramType = "path")
  ))
  def getBestHeaderAtHeightR: Route = (pathPrefix("bestHeaderAt") & height & get) { h =>
    toJsonResponse(service.getBestHeaderAtHeight(h).unsafeToFuture().map(_.asJson))
  }

  @Path("/lastHeaders/{qty}")
  @ApiOperation(
    value = "Finds `qty` last block headers",
    notes = "Returns block headers in descending order",
    httpMethod = "GET",
    response = classOf[Header],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "qty", value = "Quantity of block headers to find", required = true, dataType = "integer", paramType = "path")
  ))
  def getLastHeadersR: Route = (pathPrefix("lastHeaders") & qty & get) { q =>
    toJsonResponse(service.getLastHeaders(q).unsafeToFuture().map(_.asJson))
  }

  @Path("/headers/range/{from}/{to}")
  @ApiOperation(
    value = "Finds block headers with height in the given range",
    notes = "Returns range of headers inclusively",
    httpMethod = "GET",
    response = classOf[Header],
    responseContainer = "List")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "from", value = "Height of block", required = true, dataType = "integer", paramType = "path"),
    new ApiImplicitParam(name = "to", value = "Height of block", required = true, dataType = "integer", paramType = "path")
  ))
  def getHeadersByHeightRangeR: Route = (pathPrefix("headers" / "range") & height & height & get ) { (from, to) =>
    toJsonResponse(service.getHeadersByHeightRange(from, to).unsafeToFuture().map(_.asJson))
  }

}
