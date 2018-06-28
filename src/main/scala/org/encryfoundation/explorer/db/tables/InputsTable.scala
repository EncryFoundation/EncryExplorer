package org.encryfoundation.explorer.db.tables

object InputsTable extends Table {
  val name: String = "inputs"
  val fields: Seq[String] = Seq("id", "tx_id", "serialized_proofs")
  val fieldsString: String = fields.mkString("(", ", ", ")")
}
