package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Game {

    /* ------------ FIELDS ------------ */
    @JsonProperty("id")
    private int gameId;
    private static int numberOfGames = 0;
    private List<Player> players;
    private boolean started;
    private boolean ended;
    private String currentPlayer;
    private Purse bank;
    private Map<Currency, Building> market;
    @JsonIgnore
    private Queue<Coin> coinStack;
    @JsonIgnore
    private Queue<Building> buildingStack;
    @JsonIgnore
    private int[] scoringRound;

    /* ------------ CONSTRUCTOR ------------ */
    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames++;

        players = new LinkedList<>();
        bank = new Purse();
        market = new HashMap<>();
        market.put(Currency.BLUE, null);
        market.put(Currency.GREEN, null);
        market.put(Currency.YELLOW, null);
        market.put(Currency.ORANGE, null);

        coinStack = new LinkedList<>();
        buildingStack = new LinkedList<>();

        Populator.populateCoinStack(coinStack);
        Populator.populateBuildingStack(buildingStack);

        Random rand = new Random();
        scoringRound = new int[]{rand.nextInt(21) + 67, rand.nextInt(21) + 23};
    }


    /* ------------ GETTERS ------------ */
    public int getGameId() {
        return gameId;
    }

    public static int getNumberOfGames() {
        return numberOfGames;
    }

    public Purse getBank() {
        return bank;
    }

    public Map<Currency, Building> getMarket() {
        return market;
    }

    public Queue<Coin> getCoinStack() {
        return coinStack;
    }

    public Queue<Building> getBuildingStack() {
        return buildingStack;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getScoringRound() {
        return scoringRound;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isEnded() {
        return ended;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String playerName) {
        if (players.contains(new Player(playerName))) {
            return players.get(players.indexOf(new Player(playerName)));
        }
        return null;
    }

    @JsonProperty("playerCount")
    public int getPlayerCount() {
        return players.size();
    }

    @JsonProperty("readyCount")
    public int getReadyCount() {
        int count = 0;
        for (Player p : players) {
            if (p.isReady()) {
                count++;
            }
        }
        return count;
    }

    /* ------------ SETTERS ------------ */
    public void setCoinStack(Queue<Coin> coinStack) {
        this.coinStack = coinStack;
    }

    public void setBuildingStack(Queue<Building> buildingStack) {
        this.buildingStack = buildingStack;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setScoringRound(int[] rounds) {
        this.scoringRound = rounds;
    }


    /* ------------ PUBLIC METHODS ------------ */
    public String addPlayer(String playerName) {
        if(started){
            throw new AlhambraGameRuleException("Players can't join a started game!");
        }
        if (players.size() >= 6) {
            throw new AlhambraGameRuleException("There's no available space left for more players");
        }
        if (players.contains(new Player(playerName))) {
            throw new AlhambraGameRuleException("No unique name!");
        }

        players.add(new Player(playerName));
        return players.get(players.indexOf(new Player(playerName))).getToken();
    }

    public boolean removePlayer(String playerName) {
        Player player = getPlayerByName(playerName);
        if (player == null) {
            throw new AlhambraEntityNotFoundException("Player not present");
        }
        players.remove(player);
        if (player.getPlayerName().equals(currentPlayer)){
            TurnManager.endTurn(this);
        }
        if (players.size() < 2){
            ended = true;
        }
        return true;
    }


    /* ------------ EQUALS & HASH ------------ */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }


    /* ------------ TO STRING ------------ */

}