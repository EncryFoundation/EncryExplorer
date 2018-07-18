package models

import io.swagger.annotations.{ApiModel, ApiModelProperty}

import scala.annotation.meta.field

@ApiModel(value="Output", description="Transaction output")
case class Output(id: String,
                  txId: String,
                  @(ApiModelProperty @field)(name = "value") monetaryValue: Long,
                  coinId: String,
                  contractHash: String,
                  data: String)

object Output {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Output] = (o: Output) => Map(
    "id"           -> o.id.asJson,
    "txId"         -> o.txId.asJson,
    "value"        -> o.monetaryValue.asJson,
    "coinId"       -> o.coinId.asJson,
    "contractHash" -> o.contractHash.asJson,
    "data"         -> o.data.asJson
  ).asJson
}
