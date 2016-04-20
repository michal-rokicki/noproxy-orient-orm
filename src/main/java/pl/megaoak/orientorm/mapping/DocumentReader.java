package pl.megaoak.orientorm.mapping;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import pl.megaoak.orientorm.accessor.GenericClass;
import pl.megaoak.orientorm.exception.ObjectMappingException;
import pl.megaoak.orientorm.accessor.ClassPropertyAccessor;

import java.util.*;

import static pl.megaoak.orientorm.mapping.SpecialFields.*;

class DocumentReader {
	final private Map<Class, ClassPropertyAccessor> accessorsByClass = new HashMap<>();
	final Map<Class, TypeConverter> typeConverters;

	public DocumentReader(List<ClassPropertyAccessor> accessors, Map<Class, TypeConverter> typeConverters) {
		for (ClassPropertyAccessor accessor : accessors) {
			accessorsByClass.put(accessor.getCls(), accessor);
		}

		this.typeConverters = typeConverters;
	}

	public Object toObject(ODocument document, Class cls) {
		ClassPropertyAccessor accessor = getByClass(cls);
		Object result = accessor.createObject();

		for (String property : accessor.getRegularProperties()) {
			Object v = extractValue(document, property, accessor.getType(property));
			accessor.setValue(result, property, v);
		}

		if (document.field(RID)!=null) {
			accessor.setRidValue(result, document.field(RID));
		}

		if (document.field(VERSION)!=null) {
			accessor.setVersionValue(result, document.field(VERSION));
		}

		return result;
	}

	private Object extractValue(final ODocument document, final String property, GenericClass type) {
		if (typeConverters.containsKey(type.cls)) {
			return typeConverters.get(type.cls).fromDocument(type, new ValueExtractor() {
				@Override
				public Object extractFromDocument(GenericClass innerType) {
					return extractValue(document, property, innerType);
				}
			});
		}
		else {
			Object value = getValueOrRawValue(document, type, property);
			return toObjectPrimitiveOrCollection(value, type);
		}
	}

	private Object getValueOrRawValue(ODocument document, GenericClass type, String property) {
		if (isAccessorClassInGenericClass(type)) {
            return document.field(property);
        }
        else {
			return document.rawField(property);
		}
	}

	private boolean isAccessorClassInGenericClass(GenericClass type) {
		for (Class cls : type.classesInChain) {
			if (accessorsByClass.containsKey(cls)) {
				return true;
			}
		}

		return false;
	}

	private Object toObjectPrimitiveOrCollection(Object value, GenericClass type) {
		if (value instanceof ODocument && accessorsByClass.containsKey(type.cls)) {
			return toObject((ODocument) value, type.cls);
		}
		else if (value instanceof List) {
			return toObjectCollection((Collection)value, new ArrayList(), type.generics[0]);
		}
		else if (value instanceof Set) {
			return toObjectCollection((Collection)value, new HashSet(), type.generics[0]);
		}
		else if (value instanceof Map) {
			return toObjectMap((Map)value, type.generics[1]);
		}
		else {
			return getValueOrLink(type.cls, value);
		}
	}

	private Object getValueOrLink(Class type, Object value) {
		if (type == ORecordId.class || type == String.class) {
			if (value instanceof ODocument) {
				value = ((ODocument)value).field(RID);
			}

			if (type==String.class) {
				value = value.toString();
			}
		}
		return value;
	}

	private Map toObjectMap(Map map, GenericClass genericClass) {
		Map result = new LinkedHashMap();
		for (Object key : map.keySet()) {
			//noinspection unchecked
			result.put(key, toObjectPrimitiveOrCollection(map.get(key), genericClass));
		}

		return result;
	}

	private Collection toObjectCollection(Collection listFieldValue, Collection result, GenericClass genericClass) {
		for (Object item : listFieldValue) {
			//noinspection unchecked
			result.add(toObjectPrimitiveOrCollection(item, genericClass));
		}

		return result;
	}

	private ClassPropertyAccessor getByClass(Class cls) {
		ClassPropertyAccessor result = accessorsByClass.get(cls);
		if (result==null) {
			throw new ObjectMappingException("Class: "+cls.getName()+" is not an orient entity");
		}

		return result;
	}
}
