package org.encryfoundation.explorer.db.models

import scorex.crypto.encode.Base16

case class Output(id: String,
                  txId: String,
                  monetaryValue: Long,
                  coinId: String,
                  contractHash: String,
                  data: Array[Byte])

object Output {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Output] = (o: Output) => Map(
    "id" -> o.id.asJson,
    "parentId" -> o.txId.asJson,
    "value" -> o.monetaryValue.asJson,
    "coinId" -> o.coinId.asJson,
    "contractHash" -> o.contractHash.asJson,
    "data" -> Base16.encode(o.data).asJson
  ).asJson
}
