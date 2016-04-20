package pl.megaoak.orientorm.accessor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericFinder {

    static public GenericClass find(Type type) {
        if (type instanceof ParameterizedType) {

            ParameterizedType parameterizedType = ((ParameterizedType) type);
            Type[] generics = parameterizedType.getActualTypeArguments();

            GenericClass genericClasses[] = new GenericClass[generics.length];
            for (int i=0; i<genericClasses.length; ++i) {
                genericClasses[i] = find(generics[i]);
            }

            return new GenericClass((Class)parameterizedType.getRawType(), genericClasses);
        }

        return GenericClass.of((Class)type);
    }
}
