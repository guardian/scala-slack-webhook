package com.gu

import play.api.libs.json.Json

case class Author(author_name: String, author_link: String, author_icon: String)

object Author { implicit val authorWrites = Json.writes[Author] }

case class Field(title: String, value: String, short: Option[String] = None)

object Field { implicit val fieldWrites = Json.writes[Field] }

case class Attachment(
  title: String,
  text: String,
  fallback: String,
  fields: Option[Seq[Field]] = None,
  image_url: Option[String] = None,
  thumb_url: Option[String] = None,
  title_link: Option[String] = None,
  color: Option[String] = None,
  pretext: Option[String] = None,
  author_parameters: Option[Author] = None
) {

  def withFields(fields: Seq[Field]) = this.copy(fields = Some(fields))
  def withField(field: Field) = this.copy(fields = Some(Seq(field)))
  def withImageUrl(image_url: String) = this.copy(image_url = Some(image_url))
  def withThumbUrl(thumb_url: String) = this.copy(thumb_url = Some(thumb_url))
  def withTitleLink(title_link: String) = this.copy(title_link = Some(title_link))
  def withColor(color: String) = this.copy(color = Some(color))
  def withPretext(pretext: String) = this.copy(pretext = Some(pretext))
  def withAuthorParameters(author: Author) = this.copy(author_parameters = Some(author))
}

object Attachment { implicit val attachmentWrites = Json.writes[Attachment] }

case class Payload(
  text: String,
  channel: Option[String] = None,
  username: Option[String] = None,
  icon_url: Option[String] = None,
  icon_emoji: Option[String] = None,
  attachments: Option[Seq[Attachment]] = None
) {

  def withChannel(channel: String) = this.copy(channel = Some(channel))
  def withUsername(username: String) = this.copy(username = Some(username))
  def withIconUrl(icon_url: String) = this.copy(icon_url = Some(icon_url))
  def withIconEmoji(icon_emoji: String) = this.copy(icon_emoji = Some(icon_emoji))
  def withAttachment(attachment: Attachment) = this.copy(attachments = Some(Seq(attachment)))
  def withAttachments(attachments: Seq[Attachment]) = this.copy(attachments = Some(attachments))
}

object Payload { implicit val payloadWrites = Json.writes[Payload] }