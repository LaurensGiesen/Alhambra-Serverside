package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;


import java.util.Objects;

public class Player {

    private String playerName;
    private String token;
    private int score;
    private int virtualScore;
    private boolean ready;
    private Purse money;
    private BuildingPlace reserve;
    private City city;
    private BuildingPlace buildingInHand;


    public Player(String playerName) {
        this.playerName = playerName;
        this.money = new Purse();
        this.reserve = new BuildingPlace();
        this.city = new City();
        this.buildingInHand = new BuildingPlace();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getToken() {
        return token;
    }

    public int getVirtualScore() {
        return virtualScore;
    }

    public Purse getMoney() {
        return money;
    }

    public BuildingPlace getReserve() {
        return reserve;
    }

    public City getCity() {
        return city;
    }

    public BuildingPlace getBuildingInHand() {
        return buildingInHand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isValidToken(String token) {
        return this.token.equals(token);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void redesignCity(Building building, Location location) {
        if (building == null) {
            //if from board to reserve
            boardToReserve(location);
        } else {
            if (city.getLocation(location).getBuilding() == null) {
            //if from reserve to board
                reserveToBoard(building, location);
            } else {
                //if from reserve to replaceOnBoard
                reserveToReplace(building, location);
            }
        }
    }

    private void boardToReserve(Location location) {
        if (location == null) {
            throw new AlhambraEntityNotFoundException("Remove building from location null cannot be done");
        }
        Building b = city.removeBuilding(location);
        reserve.addBuilding(b);
    }

    private void reserveToBoard(Building building, Location location) {
        if (!reserve.getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("No such building in reserve");
        }
        reserve.removeBuilding(building);
        city.addBuilding(building, location);
    }

    private void reserveToReplace(Building building, Location location) {
        if (!reserve.getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("No such building in reserve");
        }
        reserve.removeBuilding(building);
        Building b1 = city.replaceBuilding(building, location);
        reserve.addBuilding(b1);
    }
}
