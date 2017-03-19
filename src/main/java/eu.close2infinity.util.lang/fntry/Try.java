package eu.close2infinity.util.lang.fntry;

import java.util.Optional;

public interface Try<R, E extends Exception> {

    Optional<R> result();

    Optional<E> exception();

    /**
     * Directly evaluates a function lamda but returns a functional Either
     * depending on the outcome.
     *
     * @param a the argument
     *
     * @return an Either holding the return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    static <A, R, E extends Exception> Try<R, E> doTry(ThrowingFunction<A, R> f, A a) {
        return f.tryApply(a);
    }

    /**
     * Directly evaluates a consumer lamda but returns a functional Either
     * depending on the outcome.
     *
     * @param a the argument
     *
     * @return an Either holding the (void) return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    static <A, E extends Exception> Try<Void, E> doTry(ThrowingConsumer<A> f, A a) {
        return f.tryAccept(a);
    }

    /**
     * Directly evaluates a consumer lamda but returns a functional Either
     * depending on the outcome.
     *
     * @return an Either holding the return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    static <R, E extends Exception> Try<R, E> doTry(ThrowingSupplier<R> f) {
        return f.tryGet();
    }

    class Success<R, E extends Exception> implements Try<R, E> {

        private final R result;

        Success(final R result) {
            this.result = result;
        }

        @Override
        public Optional<R> result() {
            return Optional.of(result);
        }

        @Override
        public Optional<E> exception() {
            return Optional.empty();
        }
    }

    class VoidSuccess<R, E extends Exception> implements Try<R, E> {

        @Override
        public Optional<R> result() {
            return Optional.empty();
        }

        @Override
        public Optional<E> exception() {
            return Optional.empty();
        }
    }

    class Failure<R, E extends Exception> implements Try<R, E> {

        private final E exception;

        Failure(final E exception) {
            this.exception = exception;
        }

        @Override
        public Optional<R> result() {
            return Optional.empty();
        }

        @Override
        public Optional<E> exception() {
            return Optional.of(exception);
        }
    }
}
