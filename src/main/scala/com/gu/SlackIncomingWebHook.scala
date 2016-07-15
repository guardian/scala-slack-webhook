package com.gu

import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import play.api.libs.json.{JsValue, Json}

import scala.io.Source

class SlackIncomingWebHook(url: String) extends Http {

  def send(payload: Payload): HttpResponse = {
    POST(url, Json.toJson(payload))
  }

}

trait Http {

  val client = HttpClients.createDefault()

  def POST(url: String, payload: JsValue): HttpResponse = {
    val post = new HttpPost(url)

    post.setHeader("Content-type", "application/x-www-form-urlencoded")
    post.setEntity(new StringEntity(s"payload=${payload.toString}"))

    new HttpResponse(client.execute(post))
  }
}

class HttpResponse(val response: CloseableHttpResponse) {
  lazy val responseCode = response.getStatusLine.getStatusCode
  lazy val body = Source.fromInputStream(response.getEntity.getContent).getLines().mkString("")
}