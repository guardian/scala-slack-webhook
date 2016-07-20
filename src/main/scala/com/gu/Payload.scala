package com.gu

import play.api.libs.json.Json

case class Payload(
  text: String,
  channel: Option[String] = None,
  username: Option[String] = None,
  icon_url: Option[String] = None,
  icon_emoji: Option[String] = None,
  attachments: Option[List[Attachment]] = None
  ) {

  def withChannel(channel: String) = this.copy(channel = Some(channel))
  def withUsername(username: String) = this.copy(username = Some(username))
  def withIconUrl(icon_url: String) = this.copy(icon_url = Some(icon_url))
  def withIconEmoji(icon_emoji: String) = this.copy(icon_emoji = Some(icon_emoji))
  def withAttachment(attachments: List[Attachment]) = this.copy(attachments = Some(attachments))
}

object Payload { implicit val jw = Json.writes[Payload] }
