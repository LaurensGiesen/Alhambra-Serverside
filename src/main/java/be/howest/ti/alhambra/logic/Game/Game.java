package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.*;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;
    private Set<Player> players;
    private boolean started;
    private boolean ended;



    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++;
        players = new HashSet<>();
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
            throw new AlhambraEntityNotFoundException("Get some friends!");
        };
    }

    public void endGame(){
        if (started = true){
            this.ended = true;
        } else {
            throw new AlhambraEntityNotFoundException("The game hasn't even started yet");
        }

    }


}