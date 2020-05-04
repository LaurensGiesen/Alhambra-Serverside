package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.random;

public class Player {

    private String playerName;
    private String token = randomAlphaNumeric(20);
    private int score;
    private int virtualScore;
    private boolean ready;
    private Purse money;
    private BuildingPlace reserve;
    private City city;
    private BuildingPlace buildingInHand;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Random r = new Random();

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (r.nextInt(ALPHA_NUMERIC_STRING.length()));
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

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
        if (amount < 0) {
            throw new AlhambraGameRuleException("can't add negative numbers to score");
        }
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

    public void buildBuilding(Building building, Location location) {
        if (location == null) {
            placeBuildingInReserve(building);
        } else {
            buildBuildingOnAlhambra(building, location);
        }
    }

    private void buildBuildingOnAlhambra(Building building, Location location) {
        if (!buildingInHand.getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("selected building not in hand");
        }
        city.addBuilding(building, location);
        buildingInHand.removeBuilding(building);
    }

    private void placeBuildingInReserve(Building building) {
        if (!buildingInHand.getBuildings().contains(building)) {
            throw new AlhambraEntityNotFoundException("selected building not in hand");
        }
        reserve.addBuilding(building);
        buildingInHand.removeBuilding(building);
    }

    public void addBuildingToHand(Building building) {
        buildingInHand.addBuilding(building);
    }
}
