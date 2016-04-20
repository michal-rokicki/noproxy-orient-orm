package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;

import java.io.Serializable;

@OrientClass
public class Terrain implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
