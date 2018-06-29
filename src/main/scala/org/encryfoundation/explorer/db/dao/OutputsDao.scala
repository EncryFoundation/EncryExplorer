package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Output

object OutputsDao {

  import org.encryfoundation.explorer.db.tables.OutputsTable._

  import cats.implicits._
  import doobie._
  import doobie.implicits._

  val fieldsF: Fragment = Fragment.const(fields.mkString(", "))
  val tableF: Fragment = Fragment.const(name)

  def getById(id: String): ConnectionIO[Output] = select(id).option.flatMap {
    case Some(out) => out.pure[ConnectionIO]
    case None => doobie.free.connection.raiseError(
      new NoSuchElementException(s"Cannot find header with id = $id")
    )
  }

  def findByContractHash(ch: String): ConnectionIO[List[Output]] = selectByContractHash(ch).to[List]

  private def select(id: String): Query0[Output] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE id = $id;").query[Output]

  private def selectByContractHash(ch: String): Query0[Output] =
    (fr"SELECT" ++ fieldsF ++ fr"FROM" ++ tableF ++ fr"WHERE contract_hash = $ch;").query[Output]
}
