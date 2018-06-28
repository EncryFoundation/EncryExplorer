package org.encryfoundation.explorer.db.tables

object BlocksTable extends Table {
  val name: String = "blocks"
  val fields: Seq[String] = Seq(
    "id",
    "parent_id",
    "version",
    "height",
    "ad_proofs_root",
    "state_root",
    "transactions_root",
    "ts",
    "difficulty",
    "block_size",
    "equihash_solution",
    "ad_proofs",
    "tx_qty",
    "miner_address",
    "miner_reward",
    "fees_total",
    "txs_size"
  )
  val fieldsString: String = fields.mkString("(", ", ", ")")
}
