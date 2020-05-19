package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.building.City;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Random;

public class Player {

    /* ------------ FIELDS ------------ */
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static Random r = new Random();
    @JsonProperty("name") private String playerName;
    private int score;
    private boolean ready;
    private Purse money;
    private BuildingPlace reserve;
    private City city;
    @JsonIgnore
    private String token = randomAlphaNumeric(20);
    private BuildingPlace buildingInHand;
    @JsonIgnore
    private Boolean extraTurn;


    /* ------------ CONSTRUCTOR ------------ */
    public Player(String playerName) {
        this.playerName = playerName;
        this.money = new Purse();
        this.reserve = new BuildingPlace();
        this.city = new City();
        this.buildingInHand = new BuildingPlace();
        this.extraTurn = false;
    }


    /* ------------ GETTERS ------------ */
    public String getPlayerName() {
        return playerName;
    }

    public String getToken() {
        return token;
    }

    public Purse getMoney() {
        return money;
    }

    @JsonProperty("money")
    public Coin[] getMoneyAsArray() {
        Coin[] coins = new Coin[money.getCoins().size()];
        money.getCoins().toArray(coins);
        return coins;
    }

    public BuildingPlace getReserve() {
        return reserve;
    }

    @JsonProperty("reserve")
    public Building[] getReserveAsArray() {
        Building[] buildings = new Building[reserve.getBuildings().size()];
        reserve.getBuildings().toArray(buildings);
        return buildings;
    }

    public Boolean getExtraTurn() {
        return extraTurn;
    }

    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public Building[][] getCityAsGrid() {
        return city.cityToGrid();
    }

    public BuildingPlace getBuildingInHand() {
        return buildingInHand;
    }

    public boolean isValidToken(String token) {
        return this.token.equals(token);
    }

    public int getScore() {
        return score;
    }

    public boolean isReady() {
        return ready;
    }


    /* ------------ SETTERS ------------ */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setExtraTurn(Boolean extraTurn) {
        this.extraTurn = extraTurn;
    }


    /* ------------ PUBLIC METHODS ------------ */
    public void addBuildingToHand(Building building) {
        buildingInHand.addBuilding(building);
    }

    public void addScore(int amount) {
        if (amount < 0) {
            throw new AlhambraGameRuleException("can't add negative numbers to score");
        }
        this.score += amount;
    }

    public static boolean isValidPlayername(String playerName) {
        return playerName.matches("[a-z0-9]+");
    }

    /* ------------ PRIVATE METHODS ------------ */
    private static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (r.nextInt(ALPHA_NUMERIC_STRING.length()));
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    /* ------------ EQUALS & HASH ------------ */
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
}
