package eu.close2infinity.util.lang;

import static eu.close2infinity.util.lang.ThrowingConsumer.VoidValue.VOID;

import java.util.function.Consumer;

/**
 * Interface for a consumer whose body may throw a checked exception. The
 * consumer will automatically rethrow the exception as an unchecked
 * runtime exception.
 *
 * @param <A> the argument type
 */
public interface ThrowingConsumer<A> extends Consumer<A> {

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
     * Evaluates the function but returns a functional Either depending on the
     * outcome.
     *
     * @param a the argument
     *
     * @return an Either holding the (void) return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    default <E extends Exception> Either<VoidValue, E> tryAccept(A a) {
        try {
			acceptThrowing(a);
            return Either.ofLeft(VOID);
        } catch (Exception e) {
            //noinspection unchecked
            return Either.ofRight((E) e);
        }
    }

	void acceptThrowing(A a) throws Exception;

	/**
	 * Wrapper method that converts a consumer lamda expression to a
	 * ThrowingConsumer.
	 *
	 * @param f a consumer lamda expression
	 * @param <A> the argument type
	 * @return the consumer.
	 */
	static <A> ThrowingConsumer<A> maybeThrowing(ThrowingConsumer<A> f) {
		return f;
    }

    static <A, E extends Exception> Either<VoidValue, E> tryFor(A arg, ThrowingConsumer<A> f) {
        return f.tryAccept(arg);
    }

    enum VoidValue {
        VOID
    }
}
