package de.tarent.scala.pingpong

import akka.actor.{ActorRef, ActorSystem, PoisonPill}

case object Ping
case object Pong
case object WakeUp

object Application {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem.create("scalaPingPong")

    val pingActor: ActorRef = system.actorOf(PingActor.props)

    (0 to 10).foreach {
      i => pingActor ! WakeUp
      Thread.sleep(500)
    }

    pingActor ! PoisonPill
  }

}
