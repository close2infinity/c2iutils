package eu.close2infinity.util.test;

import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class LamdaMatcher<T> extends TypeSafeMatcher<T> {

    private final Predicate<T> matcher;
    private final String description;

    public LamdaMatcher(final Predicate<T> matcher) {
        this.matcher = matcher;
        this.description = "UNSPECIFIED PREDICATE";
    }

    public LamdaMatcher(final Predicate<T> matcher, String description) {
        this.matcher = matcher;
        this.description = description;
    }

    @Override
    protected boolean matchesSafely(final T item) {
        return matcher.test(item);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(this.description);
    }

    public static <T> LamdaMatcher<T> matches(Predicate<T> predicate) {
        return new LamdaMatcher<T>(predicate);
    }
}
