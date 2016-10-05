package com.gu

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.gu.apis.SlackApiChannels
import com.gu.services.TestConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll, Matchers}
import play.api.libs.ws.ahc.AhcWSClient

class IntegrationTests extends AsyncFlatSpec with Matchers with Eventually with BeforeAndAfterAll {

  override implicit val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val config = TestConfig
  val slackWebHook: SlackIncomingWebHook = SlackIncomingWebHook(config.testWebHookUrl)
  val generalChannel = config.slackGeneralChannelId
  val randomChannel = config.slackRandomChannelId

  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val client = AhcWSClient()

  override def afterAll() = {
    client.close()
    super.afterAll()
  }

  def timestamp = DateTime.now.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"))

  "Sending a text post" should "post successfully to the default channels in Slack" in {
    val testPostText = s"Test post - text test $timestamp"

    for {
      response <- slackWebHook.send(Payload(testPostText))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel).latestMessageText should be (testPostText)
    }
  }

  "Send a post with channel" should "post successfully to the right channel Slack" in {
    val testPostText = s"Test post - channel test $timestamp"

    for {
      response <- slackWebHook
        .send(Payload(testPostText)
        .withChannel("random"))
    } yield {
      response.status should be (200)
      SlackApiChannels(randomChannel).latestMessageText should be (testPostText)
    }

  }

  "Send a post with username" should "post successfully with the correct username in Slack" in {
    val testUserName = "Test User"

    for {
      response <- slackWebHook
        .send(Payload(s"Test post - username test $timestamp")
        .withUsername(testUserName))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel).latestMessageUsername should be (testUserName)
    }

  }

  "Send a post with iconUrl" should "post successfully to Slack with the correct icon" in {
    val testIconUrl = "https://cdn3.iconfinder.com/data/icons/ikooni-outline-file-folders/128/files-03-128.png"

    for {
      response <- slackWebHook
        .send(Payload(s"Test post -icon url test $timestamp")
        .withUsername("Icon test")
        .withIconUrl(testIconUrl))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel).isLatestMessageIconUrlPresent() should be (true)
    }

  }

  "Send a post with iconEmoji" should "post successfully to Slack with the correct icon" in {
    val testIconEmoji = ":monkey_face:"

    for {
      response <- slackWebHook
        .send(Payload(s"Test post - icon emoji test $timestamp")
        .withUsername("Emoji test")
        .withIconEmoji(":monkey_face:"))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel).latestMessageIconEmoji should be (testIconEmoji)
    }
  }

  "Send a post with a simple attachment" should "post successfully to Slack" in {
    val attachment = Attachment(
      s"Test attachment $timestamp",
      "This is a test attachment",
      "Fallback text"
    )

    for {
      response <- slackWebHook
        .send(Payload(s"Test post - simple attachment test $timestamp")
        .withAttachment(attachment))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel) should have (
        'firstAttachmentTitle (attachment.title),
        'firstAttachmentText (attachment.text),
        'firstAttachmentFallback (attachment.fallback)
      )
    }
  }

  "Send a post with a field attachment" should "post successfully to Slack" in {
    val field = Field("Title = test", "Value = test", true)
    val attachment = Attachment(
      s"Test attachment $timestamp",
      "This is a test attachment",
      "Fallback text"
    ).withField(field)

    for {
      response <- slackWebHook
        .send(Payload(s"Test post - field attachment test $timestamp")
        .withAttachment(attachment))
    } yield {
      response.status should be(200)
      SlackApiChannels(generalChannel) should have (
      'firstFieldTitle (field.title),
      'firstFieldValue (field.value),
      'firstFieldShort (field.short)
      )
    }
  }

  "Send a post with an authored attachment" should "post successfully to Slack" in {
    val attachment = Attachment(
      s"Test attachment $timestamp",
      "This is a test attachment",
      "Fallback text"
    )
      .withAuthorName(s"Test author $timestamp")
      .withAuthorLink("http://www.google.co.uk")
      .withAuthorIcon("https://image.freepik.com/free-icon/male-user-shadow_318-34042.png")

    for {
      response <- slackWebHook
        .send(Payload(s"Test post - authored attachment test $timestamp")
        .withAttachment(attachment))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel) should have (
      'firstAttachmentAuthorName (attachment.author_name.get),
      'firstAttachmentAuthorIcon (attachment.author_icon.get),
      'firstAttachmentAuthorLink (attachment.author_link.get)
      )
    }
  }

  "Send a post with an attachment containing optional parameters" should "post successfully to Slack" in {
    val attachment = Attachment(
      s"Test attachment $timestamp",
      "This is a test attachment",
      "Fallback text"
    )
      .withPretext(s"Test pretext $timestamp")
      .withColor("#FEFEFD")
      .withThumbUrl("https://image.freepik.com/free-icon/thumb-up-sign_318-63754.jpg")

    for {
      response <- slackWebHook
      .send(Payload(s"Test post - optional parameter attachment test $timestamp")
      .withAttachment(attachment))
    } yield {
      response.status should be (200)
      SlackApiChannels(generalChannel) should have (
      'firstAttachmentPretext (attachment.pretext.get),
      'firstAttachmentColor (attachment.color.get.replace("#", "")),
      'firstAttachmentThumbUrl (attachment.thumb_url.get)
      )
    }
  }
}