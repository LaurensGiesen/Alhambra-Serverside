package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.*;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;
    private Set<Player> players;


    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++;
        players = new HashSet<>();
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        if(players.contains(player)) {
            players.remove(player);
        }else {
            throw new AlhambraEntityNotFoundException("Player nog present");
        }
    }


}