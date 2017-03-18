package eu.close2infinity.util.lang;

import java.io.Serializable;

public interface StringSerializable extends Serializable {

    String serialize();

    default String asString() {
        return serialize();
    }
}
