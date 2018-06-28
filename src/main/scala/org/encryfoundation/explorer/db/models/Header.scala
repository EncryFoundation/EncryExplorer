package org.encryfoundation.explorer.db.models

import doobie.Fragment
import doobie.implicits._
import doobie.util.query.Query0

case class Header(id: String,
                  parentId: String,
                  version: Short,
                  height: Int,
                  adProofsRoot: String,
                  stateRoot: String,
                  transactionsRoot: String,
                  timestamp: Long,
                  difficulty: BigInt,
                  equihashSolution: List[Int],
                  adProofs: List[Array[Byte]],
                  txsQty: Int,
                  txsSize: Int,
                  minerAddress: String,
                  minerReward: Long,
                  totalFees: Long)

object Header {

  import org.encryfoundation.explorer.db.tables.BlocksTable._

  val fieldsFragment: Fragment = Fragment.const(fields.mkString(", "))

  def select(id: String): Query0[Header] = (fr"SELECT" ++ fieldsFragment ++ fr"FROM node_headers WHERE id = $id;").query[Header]

  def selectByParentId(parentId: String): Query0[Header] =
    (fr"SELECT" ++ fieldsFragment ++ fr"FROM node_headers WHERE parent_id = $parentId").query[Header]
}
