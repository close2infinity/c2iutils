package eu.close2infinity.util.lang.fntry;

import static eu.close2infinity.util.lang.fntry.TryTest.INVALID_URL;
import static eu.close2infinity.util.lang.fntry.TryTest.VALID_URL;
import static eu.close2infinity.util.test.LamdaMatcher.matches;

import java.net.MalformedURLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ThrowingConsumerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void of_should_return_throwing_consumer_that_evaluates_correct_url() {
        final ThrowingConsumer<String> result = ThrowingConsumer.of(TryTest::throwingMethod2);
        result.accept(VALID_URL);
    }

    @Test
    public void of_should_return_throwing_consumer_that_throws_on_malformed_url() {
        final ThrowingConsumer<String> result = ThrowingConsumer.of(TryTest::throwingMethod2);

        thrown.expect(matches((Exception e) ->
                                  e instanceof RuntimeException
                                  && e.getCause() instanceof MalformedURLException));
        result.accept(INVALID_URL);
    }
}