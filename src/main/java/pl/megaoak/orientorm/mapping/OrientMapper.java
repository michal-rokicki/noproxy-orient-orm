package pl.megaoak.orientorm.mapping;

import com.orientechnologies.orient.core.record.impl.ODocument;
import pl.megaoak.orientorm.accessor.ClassPropertyAccessor;
import pl.megaoak.orientorm.accessor.ClassPropertyAccessorFactory;
import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.util.PackageScanner;

import java.util.*;

public class OrientMapper {

    final private DocumentReader reader;
    final private DocumentWriter writer;

    final private Map<Class, TypeConverter> typeConverters = new HashMap<>();

    public OrientMapper(Package ... pck) {
        this(toStrings(pck));
    }

    public OrientMapper(String packages[]) {
        List<Class<?>> classes = findClasses(packages);
        List<ClassPropertyAccessor> accessors = findAccessors(classes);

        reader = new DocumentReader(accessors, typeConverters);
        writer = new DocumentWriter(accessors, typeConverters);
    }

    private List<Class<?>> findClasses(String[] packages) {
        List<Class<?>> result = new ArrayList<>();

        for (String pck : packages) {
            result.addAll(PackageScanner.getClassesForPackage(pck));
        }

        return result;
    }

    public void addTypeConverter(Class cls, TypeConverter typeConverter) {
        typeConverters.put(cls, typeConverter);
    }

    public <T>T toObject(ODocument document, Class<T> cls) {
        //noinspection unchecked
        return (T)reader.toObject(document, cls);
    }

    public <T>List<T> toObject(Collection<ODocument> documents, Class<T> cls) {
        List<T> result = new ArrayList<>();
        for (ODocument doc : documents) {
            result.add(toObject(doc, cls));
        }

        return result;
    }

    public ODocument toDocument(Object object) {
        return writer.toDocument(object);
    }

    public List<ODocument> toDocument(Collection<?> objects) {
        List<ODocument> result = new ArrayList<>();
        for (Object obj : objects) {
            result.add(toDocument(obj));
        }

        return result;
    }

    static private String[] toStrings(Package packages[]) {
        String result[] = new String[packages.length];

        for (int i=0; i<result.length; ++i) {
            result[i] = packages[i].getName();
        }

        return result;
    }

    private List<ClassPropertyAccessor> findAccessors(List<Class<?>> classes) {
        List<ClassPropertyAccessor> accessors = new ArrayList<>();
        for (Class<?> cls : classes) {
            if (cls.isAnnotationPresent(OrientClass.class)) {
                accessors.add(ClassPropertyAccessorFactory.get(cls));
            }
        }
        return accessors;
    }
}
