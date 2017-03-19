package eu.close2infinity.util.lang.fntry;

import com.google.common.base.Supplier;
import eu.close2infinity.util.lang.fntry.Try.Failure;
import eu.close2infinity.util.lang.fntry.Try.Success;

/**
 * Interface for a supplier whose body may throw a checked exception. The
 * supplier will automatically rethrow the exception as an unchecked
 * runtime exception.
 *
 * @param <R> the return type
 */
public interface ThrowingSupplier<R> extends Supplier<R> {

    /**
     * Syntactic sugar to convert a supplier lamda into a {@link ThrowingSupplier}
     * that will rethrow any checked exception as a runtime exception.
     *
     * @param f the supplier lamda
     *
     * @return the throwing supplier lamda
     */
    static <R> ThrowingSupplier<R> of(ThrowingSupplier<R> f) {
        return f;
    }

    @Override
    default R get() {
        try {
            return getThrowing();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Evaluates the supplier but returns a functional Either depending on the
     * outcome.
     *
     * @return an Either holding the return value on the left side or any
     * exception thrown during evaluation on the right side.
     */
    default <E extends Exception> Try<R, E> tryGet() {
        try {
            return new Success<>(getThrowing());
        } catch (Exception e) {
            //noinspection unchecked
            return new Failure<>((E) e);
        }
    }

    R getThrowing() throws Exception;
}
