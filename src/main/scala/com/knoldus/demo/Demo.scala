package com.knoldus.demo

import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinPool
import com.couchbase.client.java.CouchbaseCluster
import com.knoldus.mailbox.MyActor
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by sky on 3/2/16.
  */
object Demo extends App {

  val system =ActorSystem()

  val cluster = CouchbaseCluster.create("cb01.accounts.trakinvest.io");

  val defaultBucket = cluster.openBucket("test");

  val myActor = system.actorOf(RoundRobinPool(1000).props(Props(classOf[MyActor], defaultBucket)))

  (1 to 10000000).foreach { a => myActor ! s"hello:$a" }

}
