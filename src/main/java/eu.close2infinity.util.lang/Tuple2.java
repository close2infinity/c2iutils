package eu.close2infinity.util.lang;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A simple tuple with two elements.
 */
public class Tuple2<T1, T2> {

	public final T1 _1;
	public final T2 _2;

	private Tuple2(final T1 _1, final T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	public static <T1, T2> Tuple2<T1, T2> tuple(final T1 _1, final T2 _2) {
		return new Tuple2<>(_1, _2);
	}

	public static <T1, T2> T1 element1(final Tuple2<T1, T2> tuple) {
		return tuple._1;
	}

	public static <T1, T2> T2 element2(final Tuple2<T1, T2> tuple) {
		return tuple._2;
	}

    /**
     * Use the provided {@link BiFunction} to extract data from the tuple.
     *
     * @param f the function
     *
     * @return the result of the function
     */
    public <R> R extract(final BiFunction<T1, T2, R> f) {
        return f.apply(_1, _2);
    }

    /**
     * A convenience method to create a collector that collects a stream of
     * tuples into a map from first to second element.
     *
     * @return the collector
     */
    public static <T1, T2> Collector<Tuple2<T1, T2>, ?, Map<T1, T2>> toMap() {
        return Collectors.toMap(Tuple2::element1, Tuple2::element2);
    }

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Tuple2<?, ?> tuple = (Tuple2<?, ?>) o;
		return _1.equals(tuple._1) && _2.equals(tuple._2);

	}

	@Override
	public int hashCode() {
		int result = _1.hashCode();
		result = 31 * result + _2.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", _1, _2);
	}
}
