package eu.close2infinity.util.lang;

import static eu.close2infinity.util.lang.StreamingMatcher.match;
import static eu.close2infinity.util.lang.StreamingMatcher.on;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class StreamingMatcherTest {

    @Test
    public void testMatch() throws Exception {
        final String s = "abc";

        List<Integer> result = match(s,
              on(a -> a.equals("bc"), a -> 1),
              on(a -> a.length() == 3, a -> 3),
              on(a -> a.endsWith("c"), a -> 4))
              .collect(toList());

        assertThat(result, is(equalTo(asList(3, 4))));
    }
}