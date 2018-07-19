package settings

case class ExplorerAppSettings(directory: String, postgres: PostgresSettings, restApi: RESTApiSettings)

object ExplorerAppSettings extends SettingsReader {

//  val read: ExplorerAppSettings = ConfigFactory.load("local.conf")
//    .withFallback(ConfigFactory.load).as[ExplorerAppSettings]("encry")
}
