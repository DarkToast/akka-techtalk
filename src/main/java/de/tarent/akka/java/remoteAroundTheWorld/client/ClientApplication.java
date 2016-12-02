package de.tarent.akka.java.remoteAroundTheWorld.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.tarent.akka.java.remoteAroundTheWorld.model.WakeUp;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        // We need a more explicit config object to get the `client`
        // part of the `application.conf`
        Config config = ConfigFactory.load();

        // With the config we start the client actor system.
        // To communicate with another remote system, our client systen needs to be
        // a remote system as well.
        ActorSystem system = ActorSystem.create("client", config.getConfig("client"));

        // Here starts the remote magic. To identify the remote actor,
        // we use an "actor path". Even local actors can be located with these paths.
        Future<ActorRef> pongActorFuture = system
            .actorSelection("akka.tcp://remote@127.0.0.1:2552/user/pongActor")
            .resolveOne(new Timeout(5, TimeUnit.SECONDS));

        // The `Future` object is one of the scala library. But with the `Await` class,
        // we can wait for a result. In this time, the client connects with the server
        // system and searches for the actor named `pongActor`.
        ActorRef pongActor = Await.result(pongActorFuture, Duration.Inf());

        // With the now retrieved pongActor, we can build the local `PingActor` actor.
        ActorRef pingActor = system.actorOf(PingActor.props(pongActor));

        // Finally we send 50 `WakeUps`to the PingActor, which then will "ping"
        // the remote actor and receive itself an "pong"
        for(int i = 0; i < 50; i++) {
            pingActor.tell(new WakeUp(), ActorRef.noSender());
            Thread.sleep(500);
        }

    }
}
