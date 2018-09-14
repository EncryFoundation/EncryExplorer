package models

case class Transaction(id: String,
                       fee: Long,
                       blockId: String,
                       isCoinbase: Boolean,
                       timestamp: Long,
                       proof: Option[String])