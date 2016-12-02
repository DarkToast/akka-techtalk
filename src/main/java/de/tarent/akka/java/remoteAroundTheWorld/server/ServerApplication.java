package de.tarent.akka.java.remoteAroundTheWorld.server;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ServerApplication {
    public static void main(String[] args) {
        // We need a more explicit config object to get the `server`
        // part of the `application.conf`
        Config config = ConfigFactory.load();

        // We configure the server actor system as the previous "local" systems.
        // See the `application.conf` in the resource directory for the
        // remote configuration and the post binding.
        ActorSystem remote = ActorSystem.create("remote", config.getConfig("server"));

        // We create a `PongActor` actor.
        remote.actorOf(PongActor.props(), "pongActor");
    }
}
