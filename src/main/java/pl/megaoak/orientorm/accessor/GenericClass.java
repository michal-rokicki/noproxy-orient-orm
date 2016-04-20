package pl.megaoak.orientorm.accessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GenericClass {
    static public final HashMap<Class,GenericClass> SIMPLE_TYPE_CACHE = new HashMap<>();

    public final Class cls;
    public final GenericClass[] generics;
    public final Set<Class> classesInChain = new HashSet<>();

    public GenericClass(Class cls, GenericClass[] generics) {
        this.cls = cls;
        this.generics = generics;

        addClasses(this);
    }

    private GenericClass(Class cls) {
        this.cls = cls;
        this.generics = new GenericClass[0];

        addClasses(this);
    }

    static public GenericClass of(Class cls) {
        GenericClass result = SIMPLE_TYPE_CACHE.get(cls);

        if (result==null) {
            result = new GenericClass(cls);
            SIMPLE_TYPE_CACHE.put(cls, result);
        }

        return result;
    }

    private void addClasses(GenericClass genericClass) {
        classesInChain.add(genericClass.cls);

        for (GenericClass g : genericClass.generics) {
            addClasses(g);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericClass that = (GenericClass) o;

        if (cls!=that.cls) return false;
        return Arrays.equals(generics, that.generics);

    }

    @Override
    public int hashCode() {
        int result = cls.hashCode();
        result = 31 * result + Arrays.hashCode(generics);
        return result;
    }
}
