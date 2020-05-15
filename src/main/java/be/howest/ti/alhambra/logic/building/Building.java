package be.howest.ti.alhambra.logic.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


public class Building {

    /* ------------ FIELDS ------------ */
    private Buildingtype type;
    private int cost;
    private Walling walls;


    /* ------------ CONSTRUCTOR ------------ */

    @JsonCreator
    public Building(@JsonProperty("type") Buildingtype type, @JsonProperty("cost") int cost, @JsonProperty("walls") Walling walls) {
        this.type = type;
        this.cost = cost;
        this.walls = walls;
    }


    /* ------------ GETTERS ------------ */

    public Buildingtype getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public Walling getWalls() {
        return walls;
    }

    @JsonIgnore
    public boolean isFountain() {
        return type == null;
    }


    /* ------------ EQUALS & HASH ------------ */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return cost == building.cost &&
                type == building.type &&
                Objects.equals(walls, building.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, cost, walls);
    }
}
