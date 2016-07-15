package com.gu.services

import com.typesafe.config.ConfigFactory

object TestConfig {

  val config = ConfigFactory.load()

  val testWebHookUrl = config.getString("test-web-hook-url")
  val slackApiToken = config.getString("slack-api-token")
  val slackGeneralChannelId = config.getString("slack-general-channel-id")
  val slackRandomChannelId = config.getString("slack-random-channel-id")

}
