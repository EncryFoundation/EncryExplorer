package org.encryfoundation.explorer.utils

import com.typesafe.scalalogging.{Logger, StrictLogging}

trait Logging extends StrictLogging {
  implicit val log: Logger = logger

  def logInfo(message: String): Unit = log.info(message)
  def logWarn(message: String): Unit = log.warn(message)
  def logError(message: String): Unit = log.error(message)
  def logWarn(message: String, cause: Throwable): Unit = log.warn(message, cause)
  def logError(message: String, cause: Throwable): Unit = log.error(message, cause)
}
