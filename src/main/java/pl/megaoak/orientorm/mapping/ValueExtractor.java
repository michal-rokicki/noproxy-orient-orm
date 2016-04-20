package pl.megaoak.orientorm.mapping;

import pl.megaoak.orientorm.accessor.GenericClass;

public interface ValueExtractor {
    Object extractFromDocument(GenericClass type);
}
