package eu.close2infinity.util.lang.fntry;

import java.util.function.Consumer;

import eu.close2infinity.util.lang.fntry.Try.Failure;
import eu.close2infinity.util.lang.fntry.Try.VoidSuccess;

/**
 * Interface for a consumer whose body may throw a checked exception. The
 * consumer will automatically rethrow the exception as an unchecked
 * runtime exception.
 *
 * @param <A> the argument type
 */
public interface ThrowingConsumer<A> extends Consumer<A> {

    /**
     * Syntactic sugar to convert a consumer lamda into a {@link ThrowingConsumer}
     * that will rethrow any checked exception as a runtime exception.
     *
     * @param f the consumer lamda
     *
     * @return the throwing consumer lamda
     */
    static <A> ThrowingConsumer<A> of(ThrowingConsumer<A> f) {
        return f;
    }

    @Override
    default void accept(A a) {
        try {
            acceptThrowing(a);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Evaluates the consumer but returns a functional Either depending on the
     * outcome.
     *
     * @param a the argument
     *
     * @return an Either holding the (void) return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    default <E extends Exception> Try<Void, E> tryAccept(A a) {
        try {
            acceptThrowing(a);
            return new VoidSuccess<>();
        } catch (Exception e) {
            //noinspection unchecked
            return new Failure<>((E) e);
        }
    }

    void acceptThrowing(A a) throws Exception;

}
