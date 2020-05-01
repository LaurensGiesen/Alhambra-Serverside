package be.howest.ti.alhambra.logic.Game;

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
    


}