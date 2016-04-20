package pl.megaoak.orientorm.accessor;

import com.orientechnologies.orient.core.id.ORecordId;
import pl.megaoak.orientorm.annotation.*;
import pl.megaoak.orientorm.exception.ObjectConfigurationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassPropertyAccessorFactory {

    static public ClassPropertyAccessor get(Class cls) {
        ClassPropertyAccessor result = new ClassPropertyAccessor();
        result.cls = cls;
        result.propertySetters = SetterFinder.find(cls);
        result.propertyGetters = GetterFinder.find(cls);
        result.orientName = getOrientName(cls);

        excludeMethods(result.propertyGetters);
        excludeMethods(result.propertySetters);
        excludeProperties(result);

        renameProperties(result);
        result.propertyGetters = renameProperties(result.propertyGetters);
        result.propertySetters = renameProperties(result.propertySetters);

        result.ridProperty = findPropertyWithAnnotation(result, ID.class);
        result.versionProperty = findPropertyWithAnnotation(result, Version.class);

        findRegularProperties(result);

        check(result);

        return result;
    }

    private static void findRegularProperties(ClassPropertyAccessor result) {
        result.regularProperties = new HashSet<>(result.propertyGetters.keySet());
        result.regularProperties.remove(result.ridProperty);
        result.regularProperties.remove(result.versionProperty);
    }

    private static String findPropertyWithAnnotation(
            ClassPropertyAccessor accessors, Class<? extends Annotation> annotationClass) {

        String result = findPropertyWithAnnotationInFields(accessors, annotationClass);
        if (result!=null) {
            return result;
        }

        result = findPropertyWithAnnotationInMethods(accessors.propertySetters, annotationClass);
        if (result!=null) {
            return result;
        }

        return findPropertyWithAnnotationInMethods(accessors.propertyGetters, annotationClass);
    }

    private static String findPropertyWithAnnotationInMethods(
            Map<String, AccessorMethod> propertyMethods, Class<? extends Annotation> annotationClass) {

        for (String property : propertyMethods.keySet()) {
            AccessorMethod accessor = propertyMethods.get(property);

            if (accessor.method.isAnnotationPresent(annotationClass)) {
                return property;
            }
        }

        return null;
    }

    private static String findPropertyWithAnnotationInFields(
            ClassPropertyAccessor accessors, Class<? extends Annotation> annotationClass) {

        for (Field field : accessors.cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                if (field.isAnnotationPresent(OrientProperty.class)) {
                    return field.getAnnotation(OrientProperty.class).value();
                }
                else {
                    return field.getName();
                }
            }
        }
        return null;
    }

    private static void renameProperties(ClassPropertyAccessor result) {
        for (Field field : result.cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrientProperty.class)) {
                OrientProperty newProperty = field.getAnnotation(OrientProperty.class);
                String oldProperty = field.getName();
                AccessorMethod setter = result.propertySetters.remove(oldProperty);
                AccessorMethod getter = result.propertyGetters.remove(oldProperty);

                result.propertySetters.put(newProperty.value(), setter);
                result.propertyGetters.put(newProperty.value(), getter);
            }
        }
    }

    private static Map<String, AccessorMethod> renameProperties(Map<String, AccessorMethod> methods) {
        Map<String, AccessorMethod> result = new HashMap<>();
        for (String property : methods.keySet()) {
            AccessorMethod accessor = methods.get(property);
            Method method = methods.get(property).method;

            if (method.isAnnotationPresent(OrientProperty.class)) {
                String newProperty = method.getAnnotation(OrientProperty.class).value();
                result.put(newProperty, accessor);
            }
            else {
                result.put(property, accessor);
            }
        }

        return result;
    }

    private static String getOrientName(Class cls) {
        OrientClass annotation = (OrientClass) cls.getAnnotation(OrientClass.class);
        if (annotation.value().isEmpty()) {
            return cls.getSimpleName();
        }

        return annotation.value();
    }

    private static void excludeProperties(ClassPropertyAccessor result) {
        for (Field field : result.cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrientIgnore.class)) {
                String property = field.getName();
                result.propertySetters.remove(property);
                result.propertyGetters.remove(property);
            }
        }
    }

    private static void excludeMethods(Map<String, AccessorMethod> methods) {
        List<String> excluded = new ArrayList<>();
        for (String property : methods.keySet()) {
            Method method = methods.get(property).method;

            if (method.isAnnotationPresent(OrientIgnore.class)) {
                excluded.add(property);
            }
        }

        for (String property : excluded) {
            methods.remove(property);
        }
    }

    static private void check(ClassPropertyAccessor accessor) {
        if (!accessor.propertyGetters.keySet().containsAll(accessor.propertySetters.keySet())) {
            throw new ObjectConfigurationException("Missing getters for properties: "
                    +setDiff(accessor.propertySetters.keySet(), accessor.propertyGetters.keySet())
                    +" in class: "+accessor.cls.getName());
        }

        if (!accessor.propertySetters.keySet().containsAll(accessor.propertyGetters.keySet())) {
            throw new ObjectConfigurationException("Missing setters for properties: "
                    +setDiff(accessor.propertyGetters.keySet(), accessor.propertySetters.keySet())
                    +" in class: "+accessor.cls.getName());
        }

        for (String key : accessor.propertyGetters.keySet()) {
            if (!accessor.propertySetters.get(key).type.equals(accessor.propertyGetters.get(key).type)) {
                throw new ObjectConfigurationException("Different types form getter and setter for property "+key+
                        " in class: "+accessor.cls.getName());
            }
        }

        if (!hasParameterlessPublicConstructor(accessor.getClass())) {
            throw new ObjectConfigurationException("No default constructor in class: "+accessor.cls.getName());
        }

        if (accessor.ridProperty!=null) {
            checkProperty(accessor, accessor.ridProperty, String.class, ORecordId.class);
        }

        if (accessor.versionProperty!=null) {
            checkProperty(accessor, accessor.versionProperty, String.class, Integer.class);
        }
    }

    static private void checkProperty(ClassPropertyAccessor accessor, String property, Class...possibleTypes) {
        Set<Class> possibilities = new HashSet<>(Arrays.asList(possibleTypes));
        if (!accessor.propertyGetters.containsKey(property)) {
            throw new ObjectConfigurationException("No getters and setters for property "+accessor.ridProperty);
        }

        if (!possibilities.contains(accessor.propertyGetters.get(accessor.ridProperty).type.cls)) {
            throw new ObjectConfigurationException("Property "+property+" must have one of types: "
                    +implode(possibilities));
        }
    }

    static private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    static private String implode(Collection<Class> classes) {
        String result = "";
        boolean firstProperty=true;
        for (Class cls: classes) {
            if (firstProperty) {
                firstProperty=false;
            }
            else {
                result += ", ";
            }
            result += cls.getName();
        }

        return result;
    }

    static private String setDiff(Set<String> first, Set<String> second) {
        Set<String> diff = first = new TreeSet<>(first);
        first.removeAll(second);

        String result = "";
        boolean firstProperty=true;
        for (String property : diff) {
            if (firstProperty) {
                firstProperty=false;
            }
            else {
                result += ", ";
            }
            result += property;
        }

        return result;
    }
}
