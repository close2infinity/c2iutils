package eu.close2infinity.util.lang.fntry;

import static eu.close2infinity.util.lang.fntry.Try.doTry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TryTest {

    static final String VALID_URL = "http://www.xy.org";
    static final String INVALID_URL = "MeIamNotAnURL";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void doTry_function_should_return_url_for_correct_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod1, VALID_URL);

        assertThat(result.result())
            .isPresent()
            .hasValueSatisfying(url -> assertThat(url.toString()).isEqualTo(VALID_URL));

        assertThat(result.exception()).isEmpty();
    }

    @Test
    public void doTry_function_should_return_exception_on_malformed_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod1, INVALID_URL);

        assertThat(result.result()).isEmpty();

        assertThat(result.exception())
            .isPresent()
            .hasValueSatisfying(e -> assertThat(e).isInstanceOf(MalformedURLException.class));
    }

    @Test
    public void doTry_consumer_should_return_url_for_correct_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod1, VALID_URL);

        assertThat(result.result())
            .isPresent()
            .hasValueSatisfying(url -> assertThat(url.toString()).isEqualTo(VALID_URL));

        assertThat(result.exception()).isEmpty();
    }

    @Test
    public void doTry_consumer_should_return_exception_on_malformed_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod1, INVALID_URL);

        assertThat(result.result()).isEmpty();

        assertThat(result.exception())
            .isPresent()
            .hasValueSatisfying(e -> assertThat(e).isInstanceOf(MalformedURLException.class));
    }

    @Test
    public void doTry_supplier_should_return_url_for_correct_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod3);

        assertThat(result.result())
            .isPresent()
            .hasValueSatisfying(url -> assertThat(url.toString()).isEqualTo(VALID_URL));

        assertThat(result.exception()).isEmpty();
    }

    @Test
    public void doTry_supplier_should_return_exception_on_malformed_url() {
        final Try<URL, MalformedURLException> result = doTry(TryTest::throwingMethod4);

        assertThat(result.result()).isEmpty();

        assertThat(result.exception())
            .isPresent()
            .hasValueSatisfying(e -> assertThat(e).isInstanceOf(MalformedURLException.class));
    }

    static URL throwingMethod1(final Object a) throws MalformedURLException {
        return new URL(String.valueOf(a));
    }

    static void throwingMethod2(final Object a) throws MalformedURLException {
        new URL(String.valueOf(a));
    }

    private static URL throwingMethod3() throws MalformedURLException {
        return new URL(String.valueOf(VALID_URL));
    }

    private static URL throwingMethod4() throws MalformedURLException {
        return new URL(String.valueOf(INVALID_URL));
    }
}