package de.tarent.akka.java.supervisor.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import de.tarent.akka.java.supervisor.messages.Divide;
import de.tarent.akka.java.supervisor.messages.Plus;
import de.tarent.akka.java.supervisor.messages.Result;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class Calculator extends AbstractLoggingActor {
    private final ActorRef divisionActor;
    private final ActorRef summationActor;

    public Calculator() {
        divisionActor = getContext().actorOf(DivisionActor.props());
        summationActor = getContext().actorOf(SummationActor.props());

        configReceive();
    }

    private void configReceive() {
        PartialFunction<Object, BoxedUnit> receiveFunction = ReceiveBuilder
                .match(Plus.class, p -> summationActor.tell(p, self()))
                .match(Divide.class, d -> divisionActor.tell(d, self()))
                .match(Result.class, r -> sender().tell(r, self()))
                .matchAny(o -> unhandled(o))
                .build();

        receive(receiveFunction);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        PartialFunction<Throwable, SupervisorStrategy.Directive> strategy =
            DeciderBuilder
                .match(ArithmeticException.class, e -> {
                    log().error(e, "Ou oh! Some arithmetic exception occurred: {}", e.getMessage());
                    return SupervisorStrategy.resume();
                })
                .build();

        return new OneForOneStrategy(false, strategy);
    }
}
