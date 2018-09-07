package models

case class Transaction(id: String,
                       fee: Long,
                       blockId: String,
                       isCoinbase: Boolean,
                       timestamp: Long,
                       proof: Option[String])

object Transaction {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Transaction] = (tx: Transaction) => Map(
    "id"         -> tx.id.asJson,
    "fee"        -> tx.fee.asJson,
    "blockId"    -> tx.blockId.asJson,
    "isCoinbase" -> tx.isCoinbase.asJson,
    "timestamp"  -> tx.timestamp.asJson,
    "proof"      -> tx.proof.asJson
  ).asJson
}