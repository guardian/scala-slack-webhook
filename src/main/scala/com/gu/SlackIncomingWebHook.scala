package com.gu

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SlackIncomingWebHook(url: String) {

  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def send(payload: Payload): Future[WSResponse] = {
    val client = AhcWSClient()
    client
     .url(url)
     .withHeaders("Content-type" -> "application/json")
     .post(Json.toJson(payload)) andThen { case _ => client.close() }
  }
}