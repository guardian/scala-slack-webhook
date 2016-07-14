package com.gu

import com.gu.services.TestConfig
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.{FlatSpec, Matchers}

class HttpTest extends FlatSpec with Matchers with Http {

  val config = TestConfig

  def timestamp = DateTime.now.toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"))

  "Sending a text post" should "return a 200" in {
    val response = new SlackIncomingWebHook(config.testWebHookUrl).send(Payload(s"Test post $timestamp"))
    response.responseCode should be (200)
  }


}