package pl.megaoak.orientorm.converter;

import com.google.common.base.Optional;
import pl.megaoak.orientorm.accessor.GenericClass;
import pl.megaoak.orientorm.mapping.TypeConverter;
import pl.megaoak.orientorm.mapping.ValueExtractor;

public class GuavaOptionalConverter implements TypeConverter<Optional> {

    @Override
    public Object fromDocument(GenericClass type, ValueExtractor valueExtractor) {
        return Optional.fromNullable(valueExtractor.extractFromDocument(type.generics[0]));
    }

    @Override
    public Object toDocument(Optional value) {
        return value.orNull();
    }
}
