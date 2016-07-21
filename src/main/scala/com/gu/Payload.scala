package com.gu

import play.api.libs.json.Json

case class Field(title: String, value: String, short: Boolean = false)

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
  author_name: Option[String] = None,
  author_link: Option[String] = None,
  author_icon: Option[String] = None
) {

  def withFields(fields: Seq[Field]) = this.copy(fields = Some(fields))
  def withField(field: Field) = this.copy(fields = Some(Seq(field)))
  def withImageUrl(image_url: String) = this.copy(image_url = Some(image_url))
  def withThumbUrl(thumb_url: String) = this.copy(thumb_url = Some(thumb_url))
  def withTitleLink(title_link: String) = this.copy(title_link = Some(title_link))
  def withColor(color: String) = this.copy(color = Some(color))
  def withPretext(pretext: String) = this.copy(pretext = Some(pretext))
  def withAuthorName(author_name: String) = this.copy(author_name = Some(author_name))
  def withAuthorLink(author_link: String) = this.copy(author_link = Some(author_link))
  def withAuthorIcon(author_icon: String) = this.copy(author_icon = Some(author_icon))
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