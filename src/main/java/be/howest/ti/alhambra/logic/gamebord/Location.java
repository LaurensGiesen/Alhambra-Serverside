package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.WallingDirection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Location {

    /* ------------ FIELDS ------------ */
    private int row;
    private int col;
    @JsonIgnore
    private Building building;


    /* ------------ CONSTRUCTOR ------------ */
    @JsonCreator
    public Location(@JsonProperty("row") int row, @JsonProperty("col") int col) {
        this.row = row;
        this.col = col;
        this.building = null;
    }

    public Location(int row, int col, Building building) {
        this.row = row;
        this.col = col;
        this.building = building;
    }


    /* ------------ GETTERS ------------ */
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Building getBuilding() {
        return building;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return building == null;
    }

    public boolean containsFountain() {
        return building != null && building.isFountain();
    }

    public Location getNeighbourLocation(WallingDirection direction) {
        switch (direction) {
            case NORTH:
                return new Location(row - 1, col);
            case SOUTH:
                return new Location(row + 1, col);
            case EAST:
                return new Location(row, col + 1);
            case WEST:
            default:
                return new Location(row, col - 1);
        }
    }


    /* ------------ SETTERS ------------ */
    public void setBuilding(Building b) {
        building = b;
    }


    /* ------------ EQUALS & HASH ------------ */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row &&
                col == location.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
