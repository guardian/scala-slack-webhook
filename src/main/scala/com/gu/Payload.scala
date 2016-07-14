package com.gu

import play.api.libs.json.Json

case class Payload(text: String, channel: Option[String] = None, username: Option[String] = None, iconUrl: Option[String] = None)

object Payload {

  implicit val jf = Json.format[Payload]

}
