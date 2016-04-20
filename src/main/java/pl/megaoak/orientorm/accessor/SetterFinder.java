package pl.megaoak.orientorm.accessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class SetterFinder {
    static public Map<String, AccessorMethod> find(Class cls) {
        Map<String, AccessorMethod> result = new HashMap<>();
        for (Method m : cls.getMethods()) {
            if (isSetter(m)) {
                result.put(getSetterProperty(m),
                        new AccessorMethod(m, GenericFinder.find(m.getGenericParameterTypes()[0])));
            }
        }

        return result;
    }

    static private boolean isSetter(Method m) {
        String name = m.getName();

        if (name.length()<4 || !name.startsWith("set")) {
            return false;
        }

        if( !m.getReturnType().equals(Void.TYPE)){
            return false;
        }

        if (m.getParameterTypes().length!=1) {
            return false;
        }

        if (Modifier.isStatic(m.getModifiers())) {
            return false;
        }

        return true;
    }

    static private String getSetterProperty(Method m) {
        String name = m.getName();
        String result = name.substring(3, name.length());
        return Character.toLowerCase(result.charAt(0)) + result.substring(1);
    }
}
