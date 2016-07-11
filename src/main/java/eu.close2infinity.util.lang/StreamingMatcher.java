package eu.close2infinity.util.lang;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Matches a value against an arbitrary number of predicates and applies a
 * specific transformation for each predicate that is true.</br>
 * This is a functional version of Java's Switch/Case statement, implemented
 * using Lambda expressions.
 */
public class StreamingMatcher {

    /**
     * Matches the specified argument against a list of cases and returns a
     * stream of transformation results.
     *
     * @param arg   the argument to match against
     * @param cases an arbitrary number of cases
     *
     * @return a stream of results, one for each case where the predicate
     * returns true, each being the result of the transformation function
     * attached to the case.
     */
    @SafeVarargs
    public static <A, R> Stream<R> match(final A arg, final Case<A, R>... cases) {
        return Stream.of(cases)
              .filter(c -> c.p.test(arg))
              .map(c -> c.f.apply(arg));
    }

    /**
     * Creates a new case.
     *
     * @param p the predicate against which the argument will be tested
     * @param f the transformation that will be applied to produce the result
     *          if (and only if9 the predicate evaluates to true
     * @return the case
     */
    public static <A, R> Case<A, R> on(final Predicate<A> p, final Function<A, R> f) {
        return new Case<>(p, f);
    }

    public static class Case<A, R> {
        private final Predicate<A> p;
        private final Function<A, R> f;

        Case(final Predicate<A> p, final Function<A, R> f) {
            this.p = p;
            this.f = f;
        }
    }
}
