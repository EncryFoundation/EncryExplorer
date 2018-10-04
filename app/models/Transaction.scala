package models

case class Transaction(id: String,
                       numberInBlock: Int,
                       fee: Long,
                       blockId: String,
                       isCoinbase: Boolean,
                       timestamp: Long,
                       proof: Option[String])