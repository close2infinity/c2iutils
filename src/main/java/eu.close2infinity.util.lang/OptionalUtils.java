package eu.close2infinity.util.lang;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility methods for Java 8 {@link com.google.common.base.Optional}.
 */
@SuppressWarnings({ "Guava", "OptionalUsedAsFieldOrParameterType" })
public class OptionalUtils {

    /**
     * Converts an optional value to a list.
     *
     * @param maybe an optional value
     *
     * @return a list with 0 or 1 element.
     */
    public static <T> List<T> toList(final Optional<T> maybe) {
        return maybe.map(Collections::singletonList).orElseGet(Collections::emptyList);
    }

    /**
     * Converts a list to an optional value.
     *
     * @param list                             a list.
     * @param multipleElementsHandlingStrategy a strategy that determines what should happen if the list contains more
     *                                         than one element.
     *
     * @return an optional containing the first element in the list.
     */
    public static <T> Optional<T> fromList(
        final List<T> list,
        final MultipleElementsHandlingStrategy<T> multipleElementsHandlingStrategy)
    {
        switch (list.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(list.get(0));
            default:
                return multipleElementsHandlingStrategy.apply(list);
        }
    }

    /**
     * Checks if an object is assignable to a specific type and returns an
     * optional value depending on the result.
     *
     * @param type a type
     * @param o    an object
     *
     * @return an optional value of the specified type if the object was
     * assignable, or empty if it was not.
     */
    public static <T> Optional<T> maybeInstanceOf(final Class<T> type, final Object o) {
        return type.isAssignableFrom(o.getClass()) ? Optional.of(type.cast(o)) : Optional.empty();
    }

    /**
     * Matches an optional and executes either the provided function if the
     * optional is present or the provided supplier if it is empty.
     *
     * @param opt       the optional
     * @param onPresent a function to execute if the optional has a value
     * @param onEmpty   a supplier to execute if the optional does not have a
     *                  value
     *
     * @return the return value of either function or supplier
     */
    public static <T, R> R match(final Optional<T> opt, final Function<T, R> onPresent, final Supplier<R> onEmpty) {
        return opt.isPresent() ? onPresent.apply(opt.get()) : onEmpty.get();
    }

    public interface MultipleElementsHandlingStrategy<T> extends Function<List<T>, Optional<T>> {

        static <T1> MultipleElementsHandlingStrategy<T1> ignoreMultipleElements() {
            return new Ignore<>();
        }

        static <T1> MultipleElementsHandlingStrategy<T1> throwOnMultipleElements() {
            return new Ignore<>();
        }

        class Ignore<T> implements MultipleElementsHandlingStrategy<T> {

            @Override
            public Optional<T> apply(final List<T> list) {
                return Optional.of(list.get(0));
            }
        }

        class Throw<T> implements MultipleElementsHandlingStrategy<T> {

            @Override
            public Optional<T> apply(final List<T> list) {
                throw new IllegalArgumentException(String.format("List has multiple elements: %s", list));
            }
        }
    }

    /**
     * Converts a guava optional to a java optional
     *
     * @param opt {@link com.google.common.base.Optional}
     *
     * @return {@link Optional}
     */
    public static <T> Optional<T> fromGuava(final com.google.common.base.Optional<T> opt) {
        return Optional.ofNullable(opt.orNull());
    }

    /**
     * Converts a java optional to a guava optional
     *
     * @param opt {@link Optional}
     *
     * @return {@link com.google.common.base.Optional}
     */
    @SuppressWarnings("Guava")
    public static <T> com.google.common.base.Optional<T> toGuava(final Optional<T> opt) {
        return com.google.common.base.Optional.fromNullable(opt.orElse(null));
    }
}
