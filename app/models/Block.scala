package models

import io.circe.syntax._
import io.circe.Encoder
import Header._
import Transaction._

case class Block(header: Header, payload: List[Transaction])

object Block {
  implicit val jsonEncoder: Encoder[Block] = (b: Block) => Map(
    "header" -> b.header.asJson,
    "payload" -> b.payload.asJson
  ).asJson
}
