package org.encryfoundation.explorer.settings

import java.net.InetSocketAddress

import scala.concurrent.duration.FiniteDuration

case class RESTApiSettings(bindAddress: InetSocketAddress,
                           apiKeyHash: Option[String],
                           corsAllowed: Boolean,
                           timeout: FiniteDuration)
