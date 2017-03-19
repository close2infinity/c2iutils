package eu.close2infinity.util.lang.fntry;

import java.util.function.Function;

import eu.close2infinity.util.lang.fntry.Try.Failure;
import eu.close2infinity.util.lang.fntry.Try.Success;

/**
 * Interface for a function whose body may throw a checked exception. The
 * function will automatically rethrow the exception as an unchecked
 * runtime exception.
 *
 * @param <A> the argument type
 * @param <R> the return type
 */
public interface ThrowingFunction<A, R> extends Function<A, R> {

    /**
     * Syntactic sugar to convert a function lamda into a {@link ThrowingFunction}
     * that will rethrow any checked exception as a runtime exception.
     *
     * @param f the function lamda
     *
     * @return the throwing function lamda
     */
    static <A, R> ThrowingFunction<A, R> of(ThrowingFunction<A, R> f) {
        return f;
    }

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
    default <E extends Exception> Try<R, E> tryApply(A a) {
        try {
            return new Success<>(applyThrowing(a));
        } catch (Exception e) {
            //noinspection unchecked
            return new Failure<>((E) e);
        }
    }

    R applyThrowing(A a) throws Exception;
}
