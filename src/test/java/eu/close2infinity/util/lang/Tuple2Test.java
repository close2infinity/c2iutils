package eu.close2infinity.util.lang;

import static eu.close2infinity.util.lang.Tuple2.tuple;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Tuple2Test {

    @Test
    public void apply_should_execute_bi_function_with_tuple_elements() {
        final Tuple2<String, String> sut = tuple("Roger", "Rabbit");
        final String result = sut.extract((firstName, lastName) -> String.format("%s %s", firstName, lastName));

        assertThat(result).isEqualTo("Roger Rabbit");
    }

}