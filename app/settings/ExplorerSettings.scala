package settings

import java.io.File
import java.net.InetSocketAddress
import scala.concurrent.duration.FiniteDuration
import com.typesafe.config.Config
import net.ceedubs.ficus.readers.ValueReader

case class ExplorerSettings(postgres: PostgresSettings)

object ExplorerSettings {

  implicit val fileReader: ValueReader[File] = (cfg, path) => new File(cfg.getString(path))
  implicit val byteValueReader: ValueReader[Byte] = (cfg, path) => cfg.getInt(path).toByte
  implicit val inetSocketAddressReader: ValueReader[InetSocketAddress] = { (config: Config, path: String) =>
    val split: Array[String] = config.getString(path).split(":")
    new InetSocketAddress(split(0), split(1).toInt)
  }

}
