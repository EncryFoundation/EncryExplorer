package protocol

import javax.inject.{Inject, Singleton}
import play.api.db.Database

@Singleton
class DBHandler @Inject() (db: Database)  {

  def test = println(db.getConnection.prepareStatement("select * from id"))
}
