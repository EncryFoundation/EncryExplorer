package org.encryfoundation.explorer.db.tables

object TransactionsTable extends Table {
  val name: String = "transactions"
  val fields: Seq[String] = Seq("id", "block_id", "is_coinbase", "ts")
  val fieldsString: String = fields.mkString("(", ", ", ")")
}
