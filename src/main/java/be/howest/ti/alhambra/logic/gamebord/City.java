package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.building.WallingDirection;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City {
    private List<Location> locations = new ArrayList<>();

    public City() {
        Building fountain = new Building(null, 0, new Walling(false, false, false, false));
        locations.add(new Location(0, 0, fountain));
    }

    public boolean isValidPlacing(Building building, Location location) {
        return isJoinedOnOneSide(location)
                && isReachableOnFoot(location, null)
                && hasAdjoiningSides(building.getWalls(), location)
                && leavesNoEmptySpace(location);
    }

    private boolean isJoinedOnOneSide(Location location) {
        Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
        Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
        Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
        Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

        return (lN == null || lN.isEmpty())
                && (lE == null || lE.isEmpty())
                && (lS == null || lS.isEmpty())
                && (lW == null || lW.isEmpty());
    }

    private boolean isReachableOnFoot(Location location, Set<Location> prevLocations) {
        if(prevLocations == null){
            prevLocations = new HashSet<>();
        } else if(prevLocations.contains(location)){
            return false;
        }

        prevLocations.add(location);

        if(location.containsFountain()){
            return true;
        } else {
            Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
            Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
            Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
            Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

            return (lN != null && !lN.isEmpty() && isReachableOnFoot(lN, prevLocations))
                        || (lE != null && !lE.isEmpty() && isReachableOnFoot(lE, prevLocations))
                        || (lS != null && !lS.isEmpty() && isReachableOnFoot(lS, prevLocations))
                        || (lW != null && !lW.isEmpty() && isReachableOnFoot(lW, prevLocations));
        }
    }

    private boolean hasAdjoiningSides(Walling walls, Location location) {
        Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
        Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
        Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
        Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

        return (lN == null || lN.isEmpty() || lN.getBuilding().getWalls().getWallNorth() == walls.getWallNorth())
                && (lE == null || lE.isEmpty() || lE.getBuilding().getWalls().getWallEast() == walls.getWallEast())
                && (lS == null || lS.isEmpty() || lS.getBuilding().getWalls().getWallSouth() == walls.getWallSouth())
                && (lW == null || lW.isEmpty() || lW.getBuilding().getWalls().getWallWest() == walls.getWallWest());
    }

    private boolean leavesNoEmptySpace(Location location) {
        return false;
    }



    public boolean isRemovable(Location location) {
        return false;
    }

    public void addBuilding(Building building, Location location) {

    }

    public Location getLocation(Location location) {
        if(locations.contains(location)){
            return locations.get(locations.indexOf(location));
        }
        return null;
    }

    public Building removeBuilding(Location location) {
        return null;
    }

    public Building replaceBuilding(Building b, Location location) {
        return null;
    }



    public int getAmountOfBuildings(Buildingtype type) {
        return 0;
    }

    public int getLengthWall() {
        return 0;
    }

    public List<Location> getAvailableLocations(Walling wN) {
        return null;
    }
}
