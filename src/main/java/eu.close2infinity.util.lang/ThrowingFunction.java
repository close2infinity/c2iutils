package eu.close2infinity.util.lang;

import java.util.function.Function;

/**
 * Interface for a function whose body may throw a checked exception. The
 * function will automatically rethrow the exception as an unchecked
 * runtime exception. To use, simply cast a Function lambda to this type.
 *
 * @param <A> the argument type
 * @param <R> the return type
 */
public interface ThrowingFunction<A, R> extends Function<A, R> {

	@Override
	default R apply(A a) {
		try {
			return applyThrowing(a);
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
     * @return an Either holding the return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    default <E extends Exception> Either<R, E> tryApply(A a) {
        try {
			return Either.ofLeft(applyThrowing(a));
		} catch (Exception e) {
	       return Either.ofRight((E) e);
		}
    }

	R applyThrowing(A a) throws Exception;

	/**
	 * Wrapper method that converts a lamda expression to a ThrowingFunction.
	 *
	 * @param f   a lamda expression
	 * @param <A> the argument type
	 * @param <R> the return type
	 * @return the function.
	 */
	static <A, R> ThrowingFunction<A, R> maybeThrowing(ThrowingFunction<A, R> f) {
		return f;
	}

    static <A, R, E extends Exception> Either<R, E> tryFor(A arg, ThrowingFunction<A, R> f) {
	    return f.tryApply(arg);
    }
}
