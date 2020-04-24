package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.WallingDirection;

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
}
