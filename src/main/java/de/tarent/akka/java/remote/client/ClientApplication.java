package de.tarent.akka.java.remote.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import de.tarent.akka.java.remote.model.WakeUp;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        Config config = ConfigFactory.load();

        ActorSystem system = ActorSystem.create("client", config.getConfig("client"));

        Future<ActorRef> pongActorFuture = system
            .actorSelection("akka.tcp://remote@127.0.0.1:2552/user/pongActor")
            .resolveOne(new Timeout(5, TimeUnit.SECONDS));

        ActorRef pongActor = Await.result(pongActorFuture, Duration.Inf());
        ActorRef pingActor = system.actorOf(PingActor.props(pongActor));

        for(int i = 0; i < 50; i++) {
            pingActor.tell(new WakeUp(), ActorRef.noSender());
            Thread.sleep(500);
        }

    }
}
