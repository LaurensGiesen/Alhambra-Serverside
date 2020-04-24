package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.WallingDirection;

import java.util.Objects;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Location getLocation(WallingDirection direction){
        switch(direction){
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
