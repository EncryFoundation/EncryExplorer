package org.encryfoundation.explorer.db.models


case class Output(id: String,
                  txId: String,
                  monetaryValue: Long,
                  coinId: String,
                  contractHash: String,
                  data: String)

object Output {

  import io.circe.Encoder
  import io.circe.generic.extras._

  private implicit val config: Configuration = Configuration.default.copy(
    transformMemberNames = {
      case "monetaryValue" => "value"
      case "txId" => "parentId"
      case other => other
    }
  )

  implicit val jsonEncoder: Encoder[Output] = semiauto.deriveEncoder

}
