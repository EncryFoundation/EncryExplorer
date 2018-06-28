package org.encryfoundation.explorer.db.tables

trait Table {
  val name: String
  val fields: Seq[String]
  val fieldsString: String
}
