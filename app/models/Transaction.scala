package models

case class Transaction(id: String,
                       number_in_block: Int,
                       fee: Long,
                       blockId: String,
                       isCoinbase: Boolean,
                       timestamp: Long,
                       proof: Option[String])