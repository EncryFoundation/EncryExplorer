package org.encryfoundation.explorer.settings

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

case class ExplorerAppSettings(directory: String, postgres: PostgresSettings, restApi: RESTApiSettings)

object ExplorerAppSettings extends SettingsReader {

  val read: ExplorerAppSettings = ConfigFactory.load("local.conf")
    .withFallback(ConfigFactory.load).as[ExplorerAppSettings]("encry")
}
