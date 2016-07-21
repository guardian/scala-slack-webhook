package com.gu

import play.api.libs.json.Json

case class Author(author_name: String, author_link: String, author_icon: String)

object Author { implicit val authorWrites = Json.writes[Author] }

case class Field(title: String, value: String, short: Option[String] = None)

object Field { implicit val fieldWrites = Json.writes[Field] }

case class Fields(fields: Seq[Field])

object Fields { implicit val fieldsWrites = Json.writes[Fields] }

case class Attachment(
  title: String,
  text: String,
  fallback: String,
  fields: Option[Fields] = None,
  image_url: Option[String],
  thumb_url: Option[String],
  title_link: Option[String],
  color: Option[String] = None,
  pretext: Option[String] = None,
  author_parameters: Option[Author] = None
)

object Attachment { implicit val attachmentWrites = Json.writes[Attachment] }

case class Attachments(attachment: Seq[Attachment])

object Attachments { implicit val attachmentsWrites = Json.writes[Attachments] }

case class Payload(
  text: String,
  channel: Option[String] = None,
  username: Option[String] = None,
  icon_url: Option[String] = None,
  icon_emoji: Option[String] = None,
  attachments: Option[Attachments] = None
) {

  def withChannel(channel: String) = this.copy(channel = Some(channel))
  def withUsername(username: String) = this.copy(username = Some(username))
  def withIconUrl(icon_url: String) = this.copy(icon_url = Some(icon_url))
  def withIconEmoji(icon_emoji: String) = this.copy(icon_emoji = Some(icon_emoji))
  def withAttachment(attachments: Attachments) = this.copy(attachments = Some(attachments))
}

object Payload { implicit val payloadWrites = Json.writes[Payload] }