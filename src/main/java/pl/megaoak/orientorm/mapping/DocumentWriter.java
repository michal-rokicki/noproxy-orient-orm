package pl.megaoak.orientorm.mapping;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import pl.megaoak.orientorm.accessor.ClassPropertyAccessor;
import pl.megaoak.orientorm.exception.ObjectMappingException;

import java.util.*;

import static pl.megaoak.orientorm.mapping.SpecialFields.*;

class DocumentWriter {

	final private Map<Class<?>, ClassPropertyAccessor> accessorsByClass = new HashMap<>();
	final private Map<Class, TypeConverter> typeConverters;

	public DocumentWriter(List<ClassPropertyAccessor> accessors, Map<Class, TypeConverter> typeConverters) {
		this.typeConverters = typeConverters;

		for (ClassPropertyAccessor accessor : accessors) {
			accessorsByClass.put(accessor.getCls(), accessor);
		}
	}

	public ODocument toDocument(Object object) {
		ClassPropertyAccessor accessor = getByClass(object.getClass());
		ODocument result = new ODocument(accessor.getOrientName());

		for (String property : accessor.getRegularProperties()) {
			setValue(result, property, accessor.getValue(object,property));
		}

		ORecordId rid = accessor.getRidValue(object);
		Integer version = accessor.getVersionValue(object);

		if (rid!=null) {
			result.field(RID, rid);
		}

		if (version!=null) {
			result.field(VERSION, version);
		}

		return result;
	}

	private void setValue(ODocument result, String property, Object value) {
		result.field(property, toDocumentOrPrimitive(value));
	}

	private Object toDocumentOrPrimitive(Object value) {
		if (accessorsByClass.containsKey(value.getClass())) {
			return toDocument(value);
		}
		else if (value instanceof List) {
			return toDocumentCollection((Collection)value, new ArrayList<>());
		}
		else if (value instanceof Set) {
			return toDocumentCollection((Collection)value, new HashSet<>());
		}
		else if (value instanceof Map) {
			return toDocumentMap((Map)value);
		}

		return extractDocumentValue(value);
	}

	@SuppressWarnings("unchecked")
	private Object extractDocumentValue(Object value) {
		TypeConverter converter = findConverter(value.getClass());
		if (converter!=null) {
			return toDocumentOrPrimitive(converter.toDocument(value));
		}

		return value;
	}

	@SuppressWarnings("unchecked")
	private TypeConverter findConverter(Class cls) {
		for (Class c : typeConverters.keySet()) {
			if (c.isAssignableFrom(cls)) {
				return typeConverters.get(c);
			}
		}

		return null;
	}

	private Collection toDocumentCollection(Collection objectCollection, Collection result) {
		for (Object object : objectCollection) {
			//noinspection unchecked
			result.add(toDocumentOrPrimitive(object));
		}

		return result;
	}

	private Map toDocumentMap(Map map) {
		Map result = new LinkedHashMap();
		for (Object key : map.keySet()) {
			//noinspection unchecked
			result.put(key, toDocumentOrPrimitive(map.get(key)));
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
