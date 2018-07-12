package org.encryfoundation.explorer.db.models

import io.swagger.annotations.{ApiModel, ApiModelProperty}

import scala.annotation.meta.field

@ApiModel(value="Input", description="Transaction input")
case class Input(id: String,
                 @(ApiModelProperty @field)(name = "parentId") txId: String,
                 proofs: String)

object Input {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Input] = (i: Input) => Map(
    "id"       -> i.id.asJson,
    "parentId" -> i.txId.asJson,
    "proofs"   -> i.proofs.asJson
  ).asJson
}
