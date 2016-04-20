package pl.megaoak.orientorm.mapping;

import pl.megaoak.orientorm.accessor.GenericClass;

public interface TypeConverter<T> {
    Object fromDocument(GenericClass type, ValueExtractor valueExtractor);

    Object toDocument(T value);
}
