package com.knoldus.mailbox

import java.util.concurrent.TimeUnit

import akka.actor.Actor
import com.couchbase.client.java.Bucket
import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


class MyActor(bucket: Bucket) extends Actor {


  def receive = {

    case message =>
      val timestamp = System.currentTimeMillis()
      val TTL = TimeUnit.SECONDS.toSeconds(30).toInt
      val doc = JsonObject.create().put("message", message)
      val result = Future(bucket.upsert(JsonDocument.create(s"msg::$timestamp:$message",TTL, doc), 1000, TimeUnit.SECONDS))
      result.map(a=> println("idStored:"+a.id()))
  }
}

