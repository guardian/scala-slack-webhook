package com.gu

import play.api.libs.json.Json

case class Attachment(
  fallback: String,
  title: String,
  text: String,
  fields: Option[List[Fields]],
  image_url: Option[String],
  thumb_url: Option[String],
  title_link: Option[String],
  color: Option[String] = None,
  pretext: Option[String] = None,
  author_parameters: Option[Author] = None
  )

object Attachment { implicit val jw = Json.writes[Attachment] }

abstract case class Author(author_name: String, author_link: String, author_icon: String)

object Author { implicit val jw = Json.writes[Author] }

abstract case class Fields(title: String, value: String, short: Option[String] = None)

object Fields { implicit val jw = Json.writes[Author] }