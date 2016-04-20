package pl.megaoak.orientorm.accessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class GetterFinder {
    static public Map<String, AccessorMethod> find(Class cls) {
        Map<String, AccessorMethod> result = new HashMap<>();
        for (Method m : cls.getMethods()) {
            if (isGetter(m)) {
                result.put(getProperty(m), new AccessorMethod(m, GenericFinder.find(m.getGenericReturnType())));
            }
        }

        return result;
    }

    static private boolean isGetter(Method m) {
        String name = m.getName();

        if (name.equals("getClass")) {
            return false;
        }

        if (!(name.startsWith("get") && name.length()>3) && !(name.startsWith("is") && name.length()>2)) {
            return false;
        }

        if( m.getReturnType().equals(Void.TYPE)){
            return false;
        }

        if (m.getParameterTypes().length!=0) {
            return false;
        }

        if (Modifier.isStatic(m.getModifiers())) {
            return false;
        }

        return true;
    }

    static private String getProperty(Method setter) {
        String name = setter.getName();
        String result;
        if (name.startsWith("get")) {
            result = name.substring(3, name.length());
        }
        else {
            result = name.substring(2, name.length());
        }

        return Character.toLowerCase(result.charAt(0)) + result.substring(1);
    }
}
