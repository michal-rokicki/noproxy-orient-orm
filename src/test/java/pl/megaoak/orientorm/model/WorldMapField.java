package pl.megaoak.orientorm.model;

import pl.megaoak.orientorm.annotation.OrientClass;
import pl.megaoak.orientorm.annotation.OrientIgnore;

import java.io.Serializable;

@OrientClass("WorldMapField")
public class WorldMapField implements Serializable {
    private String terrainId;

    @OrientIgnore
    private Terrain terrain;

    public String getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(String terrainId) {
        this.terrainId = terrainId;
    }


    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
