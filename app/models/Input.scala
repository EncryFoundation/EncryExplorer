package models

case class Input(id: String,
                 txId: String,
                 contractBytes: String,
                 proofs: String)

object Input {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Input] = (i: Input) => Map(
    "id"     -> i.id.asJson,
    "txId"   -> i.txId.asJson,
    "contractbytes" -> i.contractBytes.asJson,
    "proofs" -> i.proofs.asJson
  ).asJson
}