package com.gu.apis

import com.gu.Http
import com.gu.services.TestConfig
import play.api.libs.json.{JsUndefined, JsValue, Json}

import scala.io.Source

case class SlackApiChannels(channel: String) {

  val config = TestConfig
  val slackChannelHistoryUrl: String = "https://slack.com/api/channels.history"

  val channelHistoryJson: JsValue = Json.parse(Source.fromURL(s"$slackChannelHistoryUrl?token=${config.slackApiToken}&channel=$channel").mkString)
  val firstMessage: JsValue = (channelHistoryJson \ "messages")(0)

  def getLatestMessageText(): String = {
    (firstMessage \ "text").toString().replace("\"", "")
  }

  def getLatestMessageUsername(): String = {
    (firstMessage \ "username").toString()replace("\"", "")
  }

  def getLatestMessageIconEmoji(): String = {
    (firstMessage \ "icons" \ "emoji").toString()replace("\"", "")
  }

  def isLatestMessageIconUrlPresent(): Boolean = {
    (firstMessage \ "icons" \ "image_48") match {
      case icon: JsUndefined => false
      case _ => true
    }
  }

}
