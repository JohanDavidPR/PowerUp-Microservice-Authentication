package co.com.auth.api.logging;

import org.slf4j.Logger;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import java.util.function.Function;

public class ReactiveLogger {
    public static void enable(Logger logger) {
        Hooks.onEachOperator("loggingHook",
                Operators.lift((sc, sub) -> new ReactorLoggingSubscriber<>(sub, logger)));
    }
}