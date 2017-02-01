package eu.close2infinity.util.lang;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalUtilsTest {

    @Test
    public void maybeInstanceOf_shouldReturnNonEmpty_forMatchingType() {
        String s = "This is a test";
        Optional<String> result = OptionalUtils.maybeInstanceOf(String.class, s);
        assertThat(result).isEqualTo(Optional.of(s));
    }

    @Test
    public void maybeInstanceOf_shouldReturnEmpty_forMisMatchingType() {
        String s = "This is a test";
        Optional<Integer> result = OptionalUtils.maybeInstanceOf(Integer.class, s);
        assertThat(result).isEqualTo(Optional.empty());
    }
}
