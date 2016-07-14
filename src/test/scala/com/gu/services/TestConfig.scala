package com.gu.services

import com.typesafe.config.ConfigFactory

object TestConfig {

  val config = ConfigFactory.load()

  val testWebHookUrl = config.getString("test-web-hook-url")

}
