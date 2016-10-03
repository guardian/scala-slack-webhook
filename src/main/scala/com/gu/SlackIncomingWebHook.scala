package com.gu

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Future

class SlackIncomingWebHook(url: String) {

  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val client = AhcWSClient()

  def send(payload: Payload): Future[WSResponse] = {
    client
      .url(url)
      .withHeaders("Content-type" -> "application/json")
      .post(Json.toJson(payload))
  }

  //TODO: Manage client
}