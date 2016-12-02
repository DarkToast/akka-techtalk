package de.tarent.scala.pingpong

import akka.actor.{Actor, Props}
import akka.event.Logging

class PongActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Ping =>
      log.info("Receiving a PING! Will pong back.")
      sender ! Pong
  }
}

object PongActor {
  def props: Props = Props[PongActor]
}