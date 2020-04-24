package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.building.WallingDirection;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

public class City {
    private Building[][] grid = new Building[][] {{null, null, null},{null, null, null},{null, null, null}};

    public City() {
        Building fountain = new Building(null, 0, new Walling(false, false, false, false));
        grid[1][1] = fountain;

        //Temporary fix to allow testing
        Building[][] grid2 = new Building[][] {{null, null, null, null, null, null, null},{null, null, null, null, null, null, null},{null, null, null, null, null, null, null},{null, null, null, null, null, null, null},{null, null, null, null, null, null, null},{null, null, null, null, null, null, null},{null, null, null, null, null, null, null}};
        grid2[3][3] = fountain;
        grid = grid2;
        //End temporary fix
    }

    private Building getBuilding(Location location) {
        int[] gridLocation = locationToGridLocation(location);
        if(grid.length < gridLocation[0] || grid[0].length < gridLocation[1]){
            return null;
        } else {
            return grid[gridLocation[0]][gridLocation[1]];
        }
    }

    public void addBuilding(Building building, Location location) {
        //Temporary fix
        grid[location.getRow()+3][location.getCol()+3] = building;
        //End temporary fix
    }

    private int[] getLocationFountain(){
        for(int i = 0; i < grid.length; i++){
            for(int j=0; j < grid[i].length; j++){
                if(grid[i][j] != null && grid[i][j].getType() == null){
                    return new int[]{i,j};
                }
            }
        }
        throw new AlhambraEntityNotFoundException("");
    }

    private int[] locationToGridLocation(Location location){
        int[] gridLocationFountain = getLocationFountain();

        return new int[]{gridLocationFountain[0] + location.getRow() , gridLocationFountain[1] + location.getCol()};
    }

    public boolean isEmpty(Location location) {
        int[] gridLocation = locationToGridLocation(location);
        if(gridLocation[0] < 0 || grid.length < gridLocation[0] || gridLocation[1] < 0 || grid[0].length < gridLocation[1]){
            return true;
        }
        return grid[gridLocation[0]][gridLocation[1]] == null;
    }

    public boolean isReplaceable(Location location) {
        int[] gridLocation = locationToGridLocation(location);
        return !isEmpty(location) && grid[gridLocation[0]][gridLocation[1]].getType() != null;
    }

    public boolean isValidPlacing(Building building, Location location) {
        return hasAdjoiningSides(building.getWalls(), location)
                && isReachableOnFoot(location)
                && leavesNoEmptySpace(location)
                && isJoinedOnOneSide(location);
    }

    private boolean isReachableOnFoot(Location location) {
        if (location.equals(new Location(0, 0))) {
            return true;
        } else {
            Location locationN = location.getLocation(WallingDirection.NORTH);
            Location locationS = location.getLocation(WallingDirection.SOUTH);
            Location locationE = location.getLocation(WallingDirection.EAST);
            Location locationW = location.getLocation(WallingDirection.WEST);

            if(!isEmpty(locationN) && !this.getBuilding(locationN).getWalls().getWallSouth()){
                isReachableOnFoot(locationN);
            }
            if(!isEmpty(locationS) && !this.getBuilding(locationS).getWalls().getWallNorth()){
                isReachableOnFoot(locationS);
            }
            if(!isEmpty(locationE) && !this.getBuilding(locationE).getWalls().getWallWest()){
                isReachableOnFoot(locationE);
            }
            if(!isEmpty(locationW) && !this.getBuilding(locationW).getWalls().getWallEast()){
                isReachableOnFoot(locationW);
            }
            return false;
        }
    }



    private boolean isJoinedOnOneSide(Location location) {
        return !(isEmpty(location.getLocation(WallingDirection.NORTH))
                    && isEmpty(location.getLocation(WallingDirection.EAST))
                    && isEmpty(location.getLocation(WallingDirection.SOUTH))
                    && isEmpty(location.getLocation(WallingDirection.WEST)));
    }

}
