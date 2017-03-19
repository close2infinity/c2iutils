package eu.close2infinity.util.lang.fntry;

import static eu.close2infinity.util.lang.fntry.TryTest.INVALID_URL;
import static eu.close2infinity.util.lang.fntry.TryTest.VALID_URL;
import static eu.close2infinity.util.test.LamdaMatcher.matches;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ThrowingFunctionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void of_should_return_throwing_function_that_evaluates_correct_url() {
        final ThrowingFunction<String, URL> result = ThrowingFunction.of(TryTest::throwingMethod1);
        assertThat(result.apply(VALID_URL)).isInstanceOf(URL.class);
    }

    @Test
    public void of_should_return_throwing_function_that_throws_on_malformed_url() {
        final ThrowingFunction<Object, URL> result = ThrowingFunction.of(TryTest::throwingMethod1);

        thrown.expect(matches((Exception e) ->
                                  e instanceof RuntimeException
                                  && e.getCause() instanceof MalformedURLException));
        result.apply(INVALID_URL);
    }
}
