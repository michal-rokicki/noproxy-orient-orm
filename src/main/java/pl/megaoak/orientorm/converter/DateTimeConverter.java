package pl.megaoak.orientorm.converter;

import org.joda.time.DateTime;
import pl.megaoak.orientorm.accessor.GenericClass;
import pl.megaoak.orientorm.mapping.TypeConverter;
import pl.megaoak.orientorm.mapping.ValueExtractor;

import java.util.Date;

public class DateTimeConverter implements TypeConverter<DateTime> {

    @Override
    public Object fromDocument(GenericClass type, ValueExtractor valueExtractor) {
        Date date = (Date)valueExtractor.extractFromDocument(GenericClass.of(Date.class));
        return new DateTime(date.getTime());
    }

    @Override
    public Object toDocument(DateTime value) {
        return new Date(value.getMillis());
    }
}
