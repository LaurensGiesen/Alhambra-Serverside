package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.building.WallingDirection;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;


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

        return !(lN.isEmpty()
                    && lE.isEmpty()
                    && lS.isEmpty()
                    && lW.isEmpty());
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

            return (!lN.isEmpty() && !lN.getBuilding().getWalls().getWallSouth() && isReachableOnFoot(lN, prevLocations))
                        || (!lE.isEmpty() && !lE.getBuilding().getWalls().getWallWest() && isReachableOnFoot(lE, prevLocations))
                        || (!lS.isEmpty() && !lS.getBuilding().getWalls().getWallNorth() && isReachableOnFoot(lS, prevLocations))
                        || (!lW.isEmpty() && !lW.getBuilding().getWalls().getWallEast() && isReachableOnFoot(lW, prevLocations));
        }
    }

    private boolean hasAdjoiningSides(Walling walls, Location location) {
        Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
        Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
        Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
        Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

        return (lN.isEmpty() || lN.getBuilding().getWalls().getWallSouth() == walls.getWallNorth())
                && (lE.isEmpty() || lE.getBuilding().getWalls().getWallWest() == walls.getWallEast())
                && (lS.isEmpty() || lS.getBuilding().getWalls().getWallNorth() == walls.getWallSouth())
                && (lW.isEmpty() || lW.getBuilding().getWalls().getWallEast() == walls.getWallWest());
    }

    private boolean leavesNoEmptySpace(Location location) {
        Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
        Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
        Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
        Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

        return !(
                (lN.isEmpty()
                        && !this.getLocation(lN.getNeighbourLocation(WallingDirection.NORTH)).isEmpty()
                        && !this.getLocation(lN.getNeighbourLocation(WallingDirection.EAST)).isEmpty()
                        && !this.getLocation(lN.getNeighbourLocation(WallingDirection.WEST)).isEmpty()
                ) || (lE.isEmpty()
                        && !this.getLocation(lE.getNeighbourLocation(WallingDirection.NORTH)).isEmpty()
                        && !this.getLocation(lE.getNeighbourLocation(WallingDirection.EAST)).isEmpty()
                        && !this.getLocation(lE.getNeighbourLocation(WallingDirection.SOUTH)).isEmpty()
                ) || (lS.isEmpty()
                        && !this.getLocation(lS.getNeighbourLocation(WallingDirection.EAST)).isEmpty()
                        && !this.getLocation(lS.getNeighbourLocation(WallingDirection.SOUTH)).isEmpty()
                        && !this.getLocation(lS.getNeighbourLocation(WallingDirection.WEST)).isEmpty()
                ) || (lW.isEmpty()
                        && !this.getLocation(lW.getNeighbourLocation(WallingDirection.NORTH)).isEmpty()
                        && !this.getLocation(lW.getNeighbourLocation(WallingDirection.SOUTH)).isEmpty()
                        && !this.getLocation(lW.getNeighbourLocation(WallingDirection.WEST)).isEmpty()
                )
        );


    }

    public boolean isRemovable(Location location) {
        //there is a building present
        // all tiles are still reachable on foot
        // it's not the fountain

        if(this.getLocation(location).getBuilding() == null){
            throw new AlhambraEntityNotFoundException("There is no building in this location");
        }

        Location lN = this.getLocation(location.getNeighbourLocation(WallingDirection.NORTH));
        Location lE = this.getLocation(location.getNeighbourLocation(WallingDirection.EAST));
        Location lS = this.getLocation(location.getNeighbourLocation(WallingDirection.SOUTH));
        Location lW = this.getLocation(location.getNeighbourLocation(WallingDirection.WEST));

        Set<Location> prevLocations = new HashSet<>();
        prevLocations.add(location);

        return !this.getLocation(location).containsFountain()
                && (lN.isEmpty() || isReachableOnFoot(lN, prevLocations))
                && (lE.isEmpty() || isReachableOnFoot(lE, prevLocations))
                && (lS.isEmpty() || isReachableOnFoot(lS, prevLocations))
                && (lW.isEmpty() || isReachableOnFoot(lW, prevLocations));
    }

    public void addBuilding(Building building, Location location) {
        if(!isValidPlacing(building, location)){
            throw new AlhambraGameRuleException("Cannot add building here");
        }
        if(!this.getLocation(location).isEmpty()){
            throw new AlhambraGameRuleException("Cannot add building on a used location");
        }

        location.setBuilding(building);
        locations.add(location);
    }

    public Location getLocation(Location location) {
        if(locations.contains(location)){
            return locations.get(locations.indexOf(location));
        }
        return location;
    }

    public Building removeBuilding(Location location) {
        if(!isRemovable(location)){
            throw new AlhambraGameRuleException("The building on this location cannot be removed");
        }
        Building building = this.getLocation(location).getBuilding();
        locations.remove(location);
        return building;
    }

    public Building replaceBuilding(Building building, Location location) {
        if(this.getLocation(location).isEmpty()){
            throw new AlhambraEntityNotFoundException("No building in this location");
        }
        if(this.getLocation(location).containsFountain()){
            throw new AlhambraGameRuleException("Fountain cannot be replaced");
        }
        if(!isValidPlacing(building, location)){
            throw new AlhambraGameRuleException("The tile cannot be placed here");
        }
        Building b = removeBuilding(location);
        addBuilding(building, location);

        return b;
    }

    public int getAmountOfBuildings(Buildingtype type) {
        int c = 0;
        for(Location l : locations){
            if(!l.isEmpty() && l.getBuilding().getType() == type){
                c++;
            }
        }
        return c;
    }

    public int getLengthWall() {
        return 0;
    }

    public List<Location> getAvailableLocations(Walling walls) {
        List<Location> availableLocations = new ArrayList<>();
        Building b = new Building(Buildingtype.ARCADES, 0, walls);

        for(Location l : locations){
            addAvailableLocation(availableLocations, b, l);
            addAvailableLocation(availableLocations, b, l.getNeighbourLocation(WallingDirection.NORTH));
            addAvailableLocation(availableLocations, b, l.getNeighbourLocation(WallingDirection.EAST));
            addAvailableLocation(availableLocations, b, l.getNeighbourLocation(WallingDirection.SOUTH));
            addAvailableLocation(availableLocations, b, l.getNeighbourLocation(WallingDirection.WEST));
        }

        return availableLocations;
    }

    private void addAvailableLocation(List<Location> availableLocations, Building b, Location l) {
        if(isValidPlacing(b, l) && this.getLocation(l).isEmpty() && !availableLocations.contains(l)){
            availableLocations.add(l);
        }
    }

    public Building[][] cityToGrid() {
        int rowMin = getEdgeOfCity(WallingDirection.NORTH);
        int rowMax = getEdgeOfCity(WallingDirection.SOUTH);
        int colMin = getEdgeOfCity(WallingDirection.WEST);
        int colMax = getEdgeOfCity(WallingDirection.EAST);
        Building[][] grid = new Building[rowMax - rowMin  + 3][colMax - colMin  + 3];


        for(Location l : locations){
            grid[l.getRow() - rowMin + 1][l.getCol() - colMin + 1] = l.getBuilding();
        }

        return grid;
    }

    private int getEdgeOfCity(WallingDirection direction){
        int res = 0;
        for(Location l : locations){
            switch(direction){
                case NORTH:
                    res = Math.min(res, l.getRow());
                    break;
                case SOUTH:
                    res = Math.max(res, l.getRow());
                    break;
                case EAST:
                    res = Math.max(res, l.getCol());
                    break;
                case WEST:
                default:
                    res = Math.min(res, l.getCol());
            }
        }
        return res;
    }
}
