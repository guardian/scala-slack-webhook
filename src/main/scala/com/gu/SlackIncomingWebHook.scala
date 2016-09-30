package com.gu

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

class SlackIncomingWebHook(url: String) extends Http {

  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def send(payload: Payload): WSResponse = {
    implicit val client = AhcWSClient()
    val response = post(url, Json.toJson(payload))
    client.close()
    response
  }

}

trait Http {

  def post(url: String, json: JsValue)(implicit client: AhcWSClient): WSResponse = {
    val response: Future[WSResponse] = client
      .url(url)
      .withHeaders("Content-type" -> "application/json")
      .post(json)

    Await.result(response, Duration.Inf)
  }

}