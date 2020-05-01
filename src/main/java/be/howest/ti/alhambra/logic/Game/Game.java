package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.*;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;


import java.lang.reflect.Array;
import java.util.*;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;
    private Set<Player> players;
    private boolean started;
    private boolean ended;
    private Queue<Coin> coinStack;
    private Queue<Building> buildingStack;
    private String currentPlayer;
    private Purse bank;
    private Map<Currency, Building> market;
//    private Array<> scoringRound;

    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++;
        players = new HashSet<>();
        //shuffle coinStack//
        List<Coin> allCoins = new ArrayList<>(Coin.allCoins());
        Collections.shuffle(allCoins);
        coinStack = new LinkedList<>(allCoins);
        //shuffle Buildings//
        List<Building> allBuildings = new ArrayList<>(Building.allBuilding());
        Collections.shuffle(allBuildings);
        buildingStack = new LinkedList<>(allBuildings);
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

    public boolean isStarted() {
        return started;
    }

    public boolean isEnded() {
        return ended;
    }


    public void startGame(){
        if (players.size() >= 2) {
            this.started = true;
        } else {
            throw new AlhambraGameRuleException("Get some friends!");
        };
    }

    public void endGame(){
        if (started = true){
            this.ended = true;
        } else {
            throw new AlhambraGameRuleException("The game hasn't even started yet");
        };

    }



}