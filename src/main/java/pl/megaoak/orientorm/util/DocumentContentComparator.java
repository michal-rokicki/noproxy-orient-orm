package pl.megaoak.orientorm.util;

import com.orientechnologies.orient.core.record.impl.ODocument;
import pl.megaoak.orientorm.mapping.SpecialFields;

import java.util.*;

public class DocumentContentComparator {

	static public boolean equals(ODocument left, ODocument right) {
		if (!objectEquals(left.field(SpecialFields.RID),right.field(SpecialFields.RID)) ||
				!objectEquals(left.field(SpecialFields.VERSION),right.field(SpecialFields.VERSION))) {
			return false;
		}

		if (!fieldsSame(left, right)) {
			return false;
		}

		for (String property : left.fieldNames()) {
			if (!propertyValueEquals(left.field(property), right.field(property))) {
				return false;
			}
		}

		return true;
	}

	private static boolean propertyValueEquals(Object left, Object right) {
		if (left instanceof ODocument && right instanceof ODocument) {
			return equals((ODocument)left, (ODocument)right);
		}
		else if (left instanceof List && right instanceof List) {
			return listEquals((List)left, (List)right);
		}
		else if (left instanceof Set && right instanceof Set) {
			return setEquals((Set)left, (Set) right);
		}
		else if (left instanceof Map && right instanceof Map) {
			//noinspection unchecked
			return mapEquals((Map)left, (Map)right);
		}
		else {
			return objectEquals(left, right);
		}
	}

	private static boolean mapEquals(Map<String, Object> left, Map<String, Object> right) {
		if (!left.keySet().containsAll(right.keySet()) || !right.keySet().containsAll(left.keySet())) {
			return false;
		}

		for (String key : left.keySet()) {
			if (!propertyValueEquals(left.get(key),right.get(key))) {
				return false;
			}
		}

		return true;
	}

	private static boolean listEquals(List left, List right) {
		if (left.size()!=right.size()) {
			return false;
		}

		for (int i=0; i<left.size(); ++i) {
			if (!propertyValueEquals(left.get(i), right.get(i))) {
				return false;
			}
		}

		return true;
	}

	private static boolean setEquals(Set left, Set right) {
		if (left.size()!=right.size()) {
			return false;
		}

		for (Object item : left) {
			if (!setContains(right, item)) {
				return false;
			}
		}

		return true;
	}

	private static boolean setContains(Set set, Object searched) {
		for (Object item : set) {
			if (propertyValueEquals(item, searched)) {
				return true;
			}
		}
		return false;
	}


	static private boolean fieldsSame(ODocument left, ODocument right) {
		Set<String> leftFields = new HashSet<>(Arrays.asList(left.fieldNames()));
		Set<String> rightFields = new HashSet<>(Arrays.asList(right.fieldNames()));

		return leftFields.containsAll(rightFields) && rightFields.containsAll(leftFields);
	}

	static private boolean objectEquals(Object left, Object right) {
		if (left==null && right==null) {
			return true;
		}
		else if (left==null || right==null) {
			return false;
		}
		return left.equals(right);
	}
}
