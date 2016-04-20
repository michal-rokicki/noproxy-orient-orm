package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;

@OrientClass
public class FieldDemoData {
	private String name;

	public FieldDemoData() {
	}

	public FieldDemoData(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldDemoData that = (FieldDemoData) o;

		return name != null ? name.equals(that.name) : that.name == null;

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
