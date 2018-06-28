package org.encryfoundation.explorer.db.tables

object OutputsTable extends Table {
  val name: String = "outputs"
  val fields: Seq[String] = Seq("id", "tx_id", "monetary_value", "coin_id", "contract_hash", "data")
  val fieldsString: String = fields.mkString("(", ", ", ")")
}
