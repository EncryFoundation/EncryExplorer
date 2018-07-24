package models

case class Transaction(id: String, blockId: String, isCoinbase: Boolean, timestamp: Long)

object Transaction {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Transaction] = (tx: Transaction) => Map(
    "id"         -> tx.id.asJson,
    "blockId"    -> tx.blockId.asJson,
    "isCoinbase" -> tx.isCoinbase.asJson,
    "timestamp"  -> tx.timestamp.asJson
  ).asJson
}
