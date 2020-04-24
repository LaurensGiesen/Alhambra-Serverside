package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walling;
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
        return false;
    }

    public boolean isValidPlacing(Building building, Location location) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Building[] buildings : grid) {
            for (Building building : buildings) {
                if (building == null) {
                    str.append(" --- ");
                } else {
                    str.append(" ").append(building.toString()).append(" ");
                }

            }
            str.append("\n");
        }

        return str.toString();
    }
}
