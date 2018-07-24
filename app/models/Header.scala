package models

case class Header(id: String,
                  parentId: String,
                  version: Short,
                  height: Int,
                  adProofsRoot: String,
                  stateRoot: String,
                  transactionsRoot: String,
                  timestamp: Long,
                  difficulty: Long,
                  blockSize: Long,
                  equihashSolutions: String,
                  adProofsOpt: Option[String],
                  txsQty: Long,
                  minerAddress: String,
                  minerReward: Long,
                  feesTotal: Long,
                  txsSize: Long,
                  bestChain: Boolean)

object Header {

  import io.circe.Encoder
  import io.circe.syntax._

  implicit val jsonEncoder: Encoder[Header] = (h: Header) => Map(
    "id"                -> h.id.asJson,
    "parentId"          -> h.parentId.asJson,
    "version"           -> h.version.asJson,
    "height"            -> h.height.asJson,
    "adProofsRoot"      -> h.adProofsRoot.asJson,
    "stateRoot"         -> h.stateRoot.asJson,
    "transactionsRoot"  -> h.transactionsRoot.asJson,
    "timestamp"         -> h.timestamp.asJson,
    "difficulty"        -> h.difficulty.asJson,
    "blockSize"         -> h.blockSize.asJson,
    "equihashSolutions" -> h.equihashSolutions.asJson,
    "adProofsOpt"       -> h.adProofsOpt.asJson,
    "txsQty"            -> h.txsQty.asJson,
    "minerAddress"      -> h.minerAddress.asJson,
    "minerReward"       -> h.minerReward.asJson,
    "feesTotal"         -> h.feesTotal.asJson,
    "txsSize"           -> h.txsSize.asJson,
    "bestChain"         -> h.bestChain.asJson
  ).asJson
}

