package models

case class Directive(txId: String,
                     typeId: Short,
                     isValid: Boolean,
                     contractHash: String,
                     amount: Long,
                     address: String,
                     tokenIdOpt: Option[String],
                     dataField: String)