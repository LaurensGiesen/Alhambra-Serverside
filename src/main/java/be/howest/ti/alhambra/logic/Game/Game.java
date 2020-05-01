package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.*;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;
    private Set<Player> players;
    private Queue<Coin> coinStack;



    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++;
        players = new HashSet<>();
        List<Coin> allCoins = new ArrayList<>(Coin.allCoins());
        Collections.shuffle(allCoins);
        coinStack = new LinkedList<>(allCoins);
    }


    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.size() < 6) {
            players.add(player);
        } else {
            throw new AlhambraEntityNotFoundException("There's no available space left for more players");
        } ;
    }
    public void removePlayer(Player player) {
        if(players.contains(player)) {
            players.remove(player);
        }else {
            throw new AlhambraEntityNotFoundException("Player not present");
        }
    }



}