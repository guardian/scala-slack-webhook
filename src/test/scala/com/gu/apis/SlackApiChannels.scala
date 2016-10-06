package com.gu.apis

import com.gu.services.TestConfig
import play.api.libs.json.{JsUndefined, JsValue, Json}

import scala.io.Source

case class SlackApiChannels(channel: String) {

  val config = TestConfig
  val slackChannelHistoryUrl: String = "https://slack.com/api/channels.history"

  val channelHistoryJson: JsValue = Json.parse(Source.fromURL(s"$slackChannelHistoryUrl?token=${config.slackApiToken}&channel=$channel").mkString)

  val latestMessage: JsValue = (channelHistoryJson \ "messages")(0).get
  lazy val latestMessageText = getJsonAttribute(latestMessage, "text")
  lazy val latestMessageUsername = getJsonAttribute(latestMessage, "username")
  lazy val latestMessageIconEmoji = jsonToString((latestMessage \ "icons" \ "emoji").get)

  lazy val latestMessageFirstAttachment: JsValue = (latestMessage \ "attachments")(0).get
  lazy val firstAttachmentTitle = getJsonAttribute(latestMessageFirstAttachment, "title")
  lazy val firstAttachmentText = getJsonAttribute(latestMessageFirstAttachment, "text")
  lazy val firstAttachmentFallback = getJsonAttribute(latestMessageFirstAttachment, "fallback")
  lazy val firstAttachmentPretext = getJsonAttribute(latestMessageFirstAttachment, "pretext")
  lazy val firstAttachmentColor = getJsonAttribute(latestMessageFirstAttachment, "color")
  lazy val firstAttachmentThumbUrl = getJsonAttribute(latestMessageFirstAttachment, "thumb_url")

  lazy val firstAttachmentAuthorName = getJsonAttribute(latestMessageFirstAttachment, "author_name")
  lazy val firstAttachmentAuthorLink = getJsonAttribute(latestMessageFirstAttachment, "author_link")
  lazy val firstAttachmentAuthorIcon = getJsonAttribute(latestMessageFirstAttachment, "author_icon")

  lazy val firstAttachmentFirstField: JsValue = (latestMessageFirstAttachment \ "fields")(0).get
  lazy val firstFieldTitle = getJsonAttribute(firstAttachmentFirstField, "title")
  lazy val firstFieldValue = getJsonAttribute(firstAttachmentFirstField, "value")
  lazy val firstFieldShort: Boolean = getJsonAttribute(firstAttachmentFirstField, "short").toBoolean

  def isLatestMessageIconUrlPresent(): Boolean = {
    (latestMessage \ "icons" \ "image_48") match {
      case icon: JsUndefined => false
      case _ => true
    }
  }

  def jsonToString(js: JsValue): String = js.toString.replace("\"", "")

  def getJsonAttribute(js: JsValue, attribute: String): String = {
    jsonToString((js \ attribute).get)
  }

}

//TODO: Model slack channel api endpoint as a case class, make ws call to get JSON and extract to new instance of case class
