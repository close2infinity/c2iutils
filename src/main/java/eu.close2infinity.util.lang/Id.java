package eu.close2infinity.util.lang;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A generic ID type.
 *
 * @param <T> the raw ID type.
 */
public abstract class Id<T extends Serializable> implements StringSerializable {

    private final T rawId;

    protected Id(T rawId) {
        this.rawId = rawId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Id<?> id = (Id<?>) o;
        return Objects.equal(rawId, id.rawId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rawId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("rawId", rawId)
            .toString();
    }

    @Override
    public String serialize() {
        return String.valueOf(rawId);
    }
}
