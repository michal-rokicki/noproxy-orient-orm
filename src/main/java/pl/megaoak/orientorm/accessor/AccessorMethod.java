package pl.megaoak.orientorm.accessor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class AccessorMethod {
    final public Method method;
    final public GenericClass type;

    public AccessorMethod(Method method, GenericClass type) {
        this.method = method;
        this.type = type;
    }
}
