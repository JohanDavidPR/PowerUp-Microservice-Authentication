package co.com.auth.api.logging;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import reactor.core.CoreSubscriber;

public class ReactorLoggingSubscriber<T> implements CoreSubscriber<T> {

    private final CoreSubscriber<? super T> actual;
    private final Logger logger;

    public ReactorLoggingSubscriber(CoreSubscriber<? super T> actual, Logger logger) {
        this.actual = actual;
        this.logger = logger;
    }

    @Override
    public void onSubscribe(Subscription s) {
        logger.info("onSubscribe");
        actual.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
        logger.info("onNext: {}", t);
        actual.onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        logger.error("onError", t);
        actual.onError(t);
    }

    @Override
    public void onComplete() {
        logger.info("onComplete");
        actual.onComplete();
    }

    @Override
    public reactor.util.context.Context currentContext() {
        return actual.currentContext();
    }
}
