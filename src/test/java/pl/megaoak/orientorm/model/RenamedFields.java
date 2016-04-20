package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.annotation.OrientProperty;

@OrientClass
public class RenamedFields {

    private String name;

    private String renamedField;

    @OrientProperty("field2")
    private String renamedField2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OrientProperty("field")
    public String getRenamedFieldXXX() {
        return renamedField;
    }

    @OrientProperty("field")
    public void setRenamedFieldYYY(String renamedField) {
        this.renamedField = renamedField;
    }

    public String getRenamedField2() {
        return renamedField2;
    }

    public void setRenamedField2(String renamedField2) {
        this.renamedField2 = renamedField2;
    }
}
