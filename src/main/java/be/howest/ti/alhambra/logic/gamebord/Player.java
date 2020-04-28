package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.coin.Coin;

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

    //    @Override
//    public String toString() {
//        return "Player{" +
//                "playerName='" + playerName + '\'' +
//                '}';
//    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isValidToken(String token) {
        return this.token.equals(token);
    }

    public void addScore(int amount) {
        this.score += amount;
    }

}
