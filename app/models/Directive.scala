package models

case class Directive(txId: String,
                     typeId: Short,
                     isValid: Boolean,
                     contracthash: String,
                     amount: Long,
                     address: String,
                     tokenIdOpt: Option[String],
                     datafield: String)

object Directive{

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Directive] = (d: Directive) => Map(
    "txId"   -> d.txId.asJson,
    "typeId" -> d.typeId.asJson,
    "isValid" -> d.isValid.asJson,
    "contracthash" -> d.contracthash.asJson,
    "amount" -> d.amount.asJson,
    "address" -> d.address.asJson,
    "tokenIdOpt" -> d.tokenIdOpt.asJson,
    "datafield" -> d.datafield.asJson
  ).asJson
}