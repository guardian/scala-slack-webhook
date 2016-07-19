package com.gu.apis

import com.gu.Http
import com.gu.services.TestConfig
import play.api.libs.json.{JsUndefined, JsValue, Json}

import scala.io.Source

object SlackApiChannels extends Http {

  val config = TestConfig
  val slackChannelHistoryUrl: String = "https://slack.com/api/channels.history"

  def getChannelHistoryJson(channel: String): JsValue = {
    Json.parse(Source.fromURL(s"$slackChannelHistoryUrl?token=${config.slackApiToken}&channel=$channel").mkString)
  }

  def getLatestMessageText(json: JsValue): String = {
    val firstMessage = (json \ "messages")(0)
    (firstMessage \ "text").toString().replace("\"", "")
  }

  def getLatestMessageUsername(json: JsValue): String = {
    val firstMessage = (json \ "messages")(0)
    (firstMessage \ "username").toString()replace("\"", "")
  }

  def getLatestMessageIconEmoji(json: JsValue): String = {
    val firstMessage = (json \ "messages")(0)
    (firstMessage \ "icons" \ "emoji").toString()replace("\"", "")
  }

  def isLatestMessageIconUrlPresent(json: JsValue): Boolean = {
    val firstMessage = (json \ "messages")(0)
    (firstMessage \ "icons" \ "image_48") match {
      case icon: JsUndefined => false
      case _ => true
    }
  }

}
