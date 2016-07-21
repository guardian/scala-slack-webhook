package com.gu.apis

import com.gu.services.TestConfig
import play.api.libs.json.{JsUndefined, JsValue, Json}

import scala.io.Source

case class SlackApiChannels(channel: String) {

  def jsonToString(js: JsValue): String = js.toString.replace("\"", "")

  val config = TestConfig
  val slackChannelHistoryUrl: String = "https://slack.com/api/channels.history"

  val channelHistoryJson: JsValue = Json.parse(Source.fromURL(s"$slackChannelHistoryUrl?token=${config.slackApiToken}&channel=$channel").mkString)

  val latestMessage: JsValue = (channelHistoryJson \ "messages")(0)
  lazy val latestMessageText = getJsonAttribute(latestMessage, "text")
  lazy val latestMessageUsename = getJsonAttribute(latestMessage, "username")
  lazy val latestMessageIconEmoji = jsonToString(latestMessage \ "icons" \ "emoji")

  lazy val latestMessageFirstAttachment = (latestMessage \ "attachments")(0)
  lazy val firstAttachmentTitle = getJsonAttribute(latestMessageFirstAttachment, "title")
  lazy val firstAttachmentText = getJsonAttribute(latestMessageFirstAttachment, "text")
  lazy val firstAttachmentFallback = getJsonAttribute(latestMessageFirstAttachment, "fallback")


  def isLatestMessageIconUrlPresent(): Boolean = {
    (latestMessage \ "icons" \ "image_48") match {
      case icon: JsUndefined => false
      case _ => true
    }
  }

  def getJsonAttribute(js: JsValue, attribute: String): String = {
    jsonToString(js \ attribute)
  }

}
