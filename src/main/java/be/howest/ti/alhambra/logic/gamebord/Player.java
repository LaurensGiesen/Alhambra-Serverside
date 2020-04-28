package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.BuildingPlace;
import be.howest.ti.alhambra.logic.coin.Purse;

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
        return ready == player.ready;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ready);
    }

//    @Override
//    public String toString() {
//        return "Player{" +
//                "playerName='" + playerName + '\'' +
//                '}';
//    }

    
}
