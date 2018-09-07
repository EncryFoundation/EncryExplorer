package models

case class Input(id: String,
                 txId: String,
                 contractbytes: String,
                 proofs: String)

object Input {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Input] = (i: Input) => Map(
    "id"     -> i.id.asJson,
    "txId"   -> i.txId.asJson,
    "contractbytes" -> i.contractbytes.asJson,
    "proofs" -> i.proofs.asJson
  ).asJson
}