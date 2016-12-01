package de.tarent.akka.java.remote.server;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ServerApplication {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        ActorSystem remote = ActorSystem.create("remote", config.getConfig("server"));

        remote.actorOf(PongActor.props(), "pongActor");
    }
}
