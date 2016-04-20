package pl.megaoak.orientorm.mapping;

import java.lang.reflect.Type;

public class TypeAndValue<T> {
    public final Type type;
    public final T value;

    public TypeAndValue(Type type, T value) {
        this.type = type;
        this.value = value;
    }
}
