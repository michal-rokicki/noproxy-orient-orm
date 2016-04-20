package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;

@OrientClass("Renamed")
public class RenamedEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
