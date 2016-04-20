package pl.megaoak.orientorm.accessor;

import com.orientechnologies.orient.core.id.ORecordId;

import java.util.Map;
import java.util.Set;

public class ClassPropertyAccessor {
    Class<?> cls;
    String orientName;
    Map<String, AccessorMethod> propertySetters;
    Map<String, AccessorMethod> propertyGetters;
    String ridProperty;
    String versionProperty;

    Set<String> regularProperties;

    public Object createObject() {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getOrientName() {
        return orientName;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setValue(Object object, String property, Object value) {
        try {
            getSetter(property).method.invoke(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Error on "+object.getClass().getName()+"."+property+"="+
                    value.getClass().getName(), e);
        }
    }

    public Object getValue(Object object, String property) {
        try {
            return getGetter(property).method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException("Error on "+object.getClass().getName()+"."+property, e);
        }
    }

    public Set<String> getRegularProperties() {
        return regularProperties;
    }

    public void setRidValue(Object object, Object value) {
        if (ridProperty!=null) {
            if (getSetter(ridProperty).type.cls == String.class) {
                value = value.toString();
            }

            setValue(object, ridProperty, value);
        }
    }

    public ORecordId getRidValue(Object object) {
        if (ridProperty!=null) {
            Object rid = getValue(object, ridProperty);

            if (rid instanceof String) {
                return new ORecordId((String)rid);
            }
            return (ORecordId) rid;
        }
        return null;
    }

    public void setVersionValue(Object object, Object value) {
        if (versionProperty!=null) {
            setValue(object, versionProperty, value);
        }
    }

    public Integer getVersionValue(Object object) {
        if (versionProperty!=null) {
            return (Integer) getValue(object, versionProperty);
        }
        return null;
    }

    public GenericClass getType(String property) {
        return propertyGetters.get(property).type;
    }

    private AccessorMethod getSetter(String property) {
        AccessorMethod result = propertySetters.get(property);
        if (result==null) {
            throw new RuntimeException("Can't find setter for property "+property+" in class: "+cls.getName());
        }
        return result;
    }

    private AccessorMethod getGetter(String property) {
        AccessorMethod result = propertyGetters.get(property);
        if (result==null) {
            throw new RuntimeException("Can't find getter for property "+property+" in class: "+cls.getName());
        }
        return result;
    }
}
