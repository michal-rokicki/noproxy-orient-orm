package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.annotation.OrientIgnore;

@OrientClass
public class IgnoredData {
    private String someField;

    @OrientIgnore
    private String ignoredField;

    private String ignoredField2;

    public String getSomeField() {
        return someField;
    }

    public void setSomeField(String someField) {
        this.someField = someField;
    }

    public String getIgnoredField() {
        return ignoredField;
    }

    public void setIgnoredField(String ignoredField) {
        this.ignoredField = ignoredField;
    }

    @OrientIgnore
    public String getIgnoredField2() {
        return ignoredField2;
    }

    @OrientIgnore
    public void setIgnoredField2(String ignoredField2) {
        this.ignoredField2 = ignoredField2;
    }

    @OrientIgnore
    public String getXXX() {
        return "";
    }
}
