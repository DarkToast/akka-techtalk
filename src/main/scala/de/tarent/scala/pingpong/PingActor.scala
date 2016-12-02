package de.tarent.scala.pingpong

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

class PingActor extends Actor {

  val log = Logging(context.system, this)

  val pongActor: ActorRef = context.actorOf(PongActor.props)

  override def receive: Receive = {
    case WakeUp =>
      log.info("WakingUp. Will send a ping.")
      pongActor ! Ping

    case Pong =>
      log.info("Receiving a pong")
  }
}

object PingActor {
  def props: Props = Props[PingActor]
}
