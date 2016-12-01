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

/**
 * Here we're not inheriting the `UntypedActor` but another
 * abstract class, which provides a java 8 lambda style to matching
 * Message types.
 */
public class Calculator extends AbstractLoggingActor {
    private final ActorRef divisionActor;
    private final ActorRef summationActor;

    public Calculator() {
        divisionActor = getContext().actorOf(DivisionActor.props());
        summationActor = getContext().actorOf(SummationActor.props());

        configReceive();
    }

    // With a matching `PartialFunction` and the `receive()` method,
    // we can configure the actor in a more functional style.
    private void configReceive() {
        PartialFunction<Object, BoxedUnit> receiveFunction = ReceiveBuilder
                .match(Plus.class, p -> summationActor.tell(p, self()))
                .match(Divide.class, d -> divisionActor.tell(d, self()))
                .match(Result.class, r -> sender().tell(r, self()))
                .matchAny(o -> unhandled(o))
                .build();

        receive(receiveFunction);
    }

    /**
     * This is the supervisor strategy, which handles ALL exception of
     * child actors. In this case the `SummationActor` and the `DivisionActor`.
     * @return
     */
    @Override
    public SupervisorStrategy supervisorStrategy() {
        PartialFunction<Throwable, SupervisorStrategy.Directive> strategy =
            DeciderBuilder
                .match(ArithmeticException.class, e -> {
                    log().error(e, "Ou oh! Some arithmetic exception occurred: {}", e.getMessage());
                    // On an ArithmeticException, we restart the child actor.
                    // So all states of the actor are purged.
                    return SupervisorStrategy.restart();
                })
                .build();

        return new OneForOneStrategy(false, strategy);
    }
}
