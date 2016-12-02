package de.tarent.akka.java.supervision;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import de.tarent.akka.java.supervision.actors.Calculator;
import de.tarent.akka.java.supervision.actors.Terminator;
import de.tarent.akka.java.supervision.messages.Divide;
import de.tarent.akka.java.supervision.messages.Plus;

public class AkkaApplication {

    public static void main(String[] args) throws Exception {
        ActorSystem basic = ActorSystem.create("Supervisor");

        ActorRef calculator = basic.actorOf(Props.create(Calculator.class), "calculator");
        basic.actorOf(Props.create(Terminator.class, calculator), "terminator");

        calculator.tell(new Plus(1, 4), ActorRef.noSender());       // 1 + 4
        calculator.tell(new Divide(4, 2), ActorRef.noSender());     // 4 / 2
        calculator.tell(new Plus(4, 0), ActorRef.noSender());       // 4 + 0
        calculator.tell(new Divide(4, 0), ActorRef.noSender());     // 4 / 0
        calculator.tell(new Divide(16, 4), ActorRef.noSender());    // 16 / 4
        calculator.tell(new Plus(8, 15), ActorRef.noSender());      // 8 + 15

        Thread.sleep(1000);
        calculator.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
