package com.gu

import com.gu.apis.SlackApiChannels
import com.gu.services.TestConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FlatSpec, Matchers}

class HttpTest extends FlatSpec with Matchers with Http with Eventually {

  override implicit val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val config = TestConfig

  def timestamp = DateTime.now.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"))

  "Sending a text post" should "return a 200" in {

    val testPostText = s"Test post - text test $timestamp"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(testPostText))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageText(SlackApiChannels.getChannelHistoryJson(config.slackGeneralChannelId)) should be (testPostText)
    }
  }

  "Send a post with channel" should "return a 200" in {

    val testPostText = s"Test post - channel test $timestamp"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(testPostText).withChannel("random"))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageText(SlackApiChannels.getChannelHistoryJson(config.slackRandomChannelId)) should be (testPostText)
    }
  }

  "Send a post with username" should "return a 200" in {
    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post - username test $timestamp").withUsername("Test User"))
    response.responseCode should be (200)
  }

  "Send a post with iconUrl" should "return a 200" in {
    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post -icon url test $timestamp").withUsername("Icon test").withIconUrl("https://cdn3.iconfinder.com/data/icons/ikooni-outline-file-folders/128/files-03-128.png"))
    response.responseCode should be (200)
  }

  "Send a post with iconEmoji" should "return a 200" in {
    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post - icon emoji test $timestamp").withUsername("Emoji test")withIconEmoji(":monkey_face:"))
    response.responseCode should be (200)
  }

}