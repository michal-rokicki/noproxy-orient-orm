package pl.megaoak.orientorm.model;

import com.orientechnologies.orient.core.id.ORecordId;
import pl.megaoak.orientorm.annotation.ID;
import pl.megaoak.orientorm.annotation.OrientClass;

import java.util.List;

@OrientClass
public class WorldMap {
    @ID
    private ORecordId id;

    private int width;
    private int height;
    private String name;
    private List<WorldMapField> fields;

    public ORecordId getId() {
        return id;
    }

    public void setId(ORecordId id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorldMapField> getFields() {
        return fields;
    }

    public void setFields(List<WorldMapField> fields) {
        this.fields = fields;
    }
}
