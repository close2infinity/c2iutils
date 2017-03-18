package eu.close2infinity.util.lang;

import java.util.Optional;
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
	default void accept(A arg) {
		try {
			acceptThrowing(arg);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    default <E extends Exception> Optional<E> tryAccept(A arg) {
		try {
			acceptThrowing(arg);
			return Optional.empty();
		} catch (Exception e) {
            //noinspection unchecked
            return Optional.of((E) e);
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

    static <A, E extends Exception> Optional<E> tryFor(A arg, ThrowingConsumer<A> f) {
        return f.tryAccept(arg);
    }
}
