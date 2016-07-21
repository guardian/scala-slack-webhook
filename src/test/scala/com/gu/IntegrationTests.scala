package com.gu

import com.gu.apis.SlackApiChannels
import com.gu.services.TestConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FlatSpec, Matchers}

class IntegrationTests extends FlatSpec with Matchers with Http with Eventually {

  override implicit val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val config = TestConfig

  def timestamp = DateTime.now.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"))

  "Sending a text post" should "post successfully to the default channels in Slack" in {
    val testPostText = s"Test post - text test $timestamp"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(testPostText))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageText(SlackApiChannels.getChannelHistoryJson(config.slackGeneralChannelId)) should be (testPostText)
    }
  }

  "Send a post with channel" should "post successfully to the right channel Slack" in {
    val testPostText = s"Test post - channel test $timestamp"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(testPostText).withChannel("random"))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageText(SlackApiChannels.getChannelHistoryJson(config.slackRandomChannelId)) should be (testPostText)
    }
  }

  "Send a post with username" should "post successfully with the correct username in Slack" in {
    val testUserName = "Test User"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post - username test $timestamp").withUsername(testUserName))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageUsername(SlackApiChannels.getChannelHistoryJson(config.slackGeneralChannelId)) should be (testUserName)
    }
  }

  "Send a post with iconUrl" should "post successfully to Slack with the correct icon" in {
    val testIconUrl = "https://cdn3.iconfinder.com/data/icons/ikooni-outline-file-folders/128/files-03-128.png"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post -icon url test $timestamp").withUsername("Icon test").withIconUrl(testIconUrl))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.isLatestMessageIconUrlPresent(SlackApiChannels.getChannelHistoryJson(config.slackGeneralChannelId)) should be (true)
    }
  }

  "Send a post with iconEmoji" should "post successfully to Slack with the correct icon" in {
    val testIconEmoji = ":monkey_face:"

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post - icon emoji test $timestamp").withUsername("Emoji test")withIconEmoji(":monkey_face:"))
    response.responseCode should be (200)

    eventually {
      SlackApiChannels.getLatestMessageIconEmoji(SlackApiChannels.getChannelHistoryJson(config.slackGeneralChannelId)) should be (testIconEmoji)
    }
  }

  "Send a post with a simple attachment" should "post successfully to Slack" in {
    val attachment = new Attachment(s"Test attachment $timestamp", "This is a test attachment", "Fallback text")
    val attachments = new Attachments(Seq(attachment))

    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post - simple attachment test $timestamp").withAttachment(attachments))
    response.responseCode should be (200)
  }
}