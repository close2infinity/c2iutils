package eu.close2infinity.util.lang;

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
}
