package com.gu

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Future

case class SlackIncomingWebHook(webhookUrl: String) {

  def send(payload: Payload)(implicit client: AhcWSClient, actorSystem: ActorSystem, materializer: ActorMaterializer): Future[WSResponse] = {
    client
     .url(webhookUrl)
     .withHeaders("Content-type" -> "application/json")
     .post(Json.toJson(payload))
  }
}