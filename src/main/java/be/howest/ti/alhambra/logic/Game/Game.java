package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.*;

public class Game {
    private int gameId;
    private static int numberOfGames = 0;
    private List<Player> players;
    private boolean started;
    private boolean ended;
    private Player currentPlayer;
    private Purse bank;
    private Map<Currency, Building> market;
    private Queue<Coin> coinStack;
    private Queue<Building> buildingStack;
    private int[] scoringRound;

    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames++;

        players = new LinkedList<>();
        bank = new Purse();
        market = new HashMap<>();

        List<Coin> allCoins = new ArrayList<>(Coin.allCoins());
        Collections.shuffle(allCoins);
        coinStack = new LinkedList<>(allCoins);

        List<Building> allBuildings = new ArrayList<>(Building.allBuilding());
        Collections.shuffle(allBuildings);
        buildingStack = new LinkedList<>(allBuildings);

        Random rand = new Random();
        scoringRound = new int[]{rand.nextInt(21) + 23, rand.nextInt(21) + 67};
    }


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

    public Player getCurrentPlayer() {
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


    public Player getPlayerByName(String playerName){
        if(players.contains(new Player(playerName))){
            return players.get(players.indexOf(new Player(playerName)));
        }
        return null;
    }

    public void addPlayer(String playerName) {
        //Max 6 players
        //Unique player necessary!

        if(players.size() >= 6){
            throw new AlhambraGameRuleException("There's no available space left for more players");
        }
        if(players.contains(new Player(playerName))){
            throw new AlhambraGameRuleException("No unique name!");
        }

        players.add(new Player(playerName));
    }

    public void removePlayer(String playerName) {
        //Player must be present
        //remove player from players
        //if current player -> end round

        Player player = getPlayerByName(playerName);
        if (player == null) {
            throw new AlhambraEntityNotFoundException("Player not present");
        }

        players.remove(player);
    }
    public void takeMoney(String playerName, Purse coins) {
        //player is present
        //player is currentPlayer
        //coins are available in bank
        //if more as 2 coins, value lower as 5
        if(this.getPlayerByName(playerName) == null){
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if(this.getPlayerByName(playerName) != currentPlayer){
            throw new AlhambraGameRuleException("Not the current player");
        }
        for(Coin coin : coins.getCoins()){
            if(!bank.getCoins().contains(coin)){
                throw new AlhambraEntityNotFoundException("No such coin in Bank");
            }
        }
        if(coins.getNumberOfCoins() > 1 && coins.getTotalAmount() > 5){
            throw new AlhambraGameRuleException("2 coins with a value higher as 5");
        }

        for(Coin coin : coins.getCoins()){
            bank.removeCoin(coin);
            this.getPlayerByName(playerName).getMoney().addCoin(coin);
        }
    }

    public void buyBuilding(String playerName, Purse coins) {
        //player is present
        //player is currentPlayer
        //coins from 1 color
        //building present in currency
        //enough coins for building
        //building from market to buildingInHand of player
        if(this.getPlayerByName(playerName) == null){
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if(this.getPlayerByName(playerName) != currentPlayer){
            throw new AlhambraGameRuleException("Not the current player");
        }
        Currency currency = coins.getCurrency();
        Building building = market.get(currency);
        if(building == null){
            throw new AlhambraEntityNotFoundException("No building in that currency");
        }
        if(building.getCost() > coins.getTotalAmount()){
            throw new AlhambraGameRuleException("Not enough money to buy");
        }
        market.put(currency, null);
        getPlayerByName(playerName).addBuildingToHand(building);
    }
    private void createMarket() {
        market.put(Currency.BLUE, buildingStack.poll());
        market.put(Currency.GREEN, buildingStack.poll());
        market.put(Currency.YELLOW, buildingStack.poll());
        market.put(Currency.ORANGE, buildingStack.poll());
    }
    private void populateMarket() {
        //buildingStack is not empty -> end of game
        //buildings van stack to market
        if (market == null) {
            createMarket();
        }
        for (Currency c : market.keySet()) {
            market.computeIfAbsent(c, k -> buildingStack.poll());
        }


    }

    private void populateCoinStack() {
        for (int i = 0; i < 108; i++) {
            coinStack = new LinkedList<>(Coin.allCoins());
        }
    }

    private void populateBank() {
        //coinStack is not empty -> replenish coinStack
        //coins van stack to bank
        //check for scoringRound
        if (coinStack.isEmpty()) {
            populateCoinStack();
        }
        if (bank.getCoins().size() < 4) {
            for (int i = bank.getCoins().size(); i < 4; i++) {
                bank.addCoin(coinStack.poll());
                if (coinStack.size() == scoringRound[0] || coinStack.size() == scoringRound[1]) {
                    score();
                }
            } 
        }
    }

    public void endOfTurn(){
        populateBank();
        populateMarket();
        setCurrentPlayer();

    }

    private void score() {
        //get correct scoreTable
        //most buildings of kind
        //Longest wall
    }

    public void startGame() {
        //min 2 players
        if (players.size() >= 2) {
            this.started = true;
        } else {
            throw new AlhambraGameRuleException("Get some friends!");
        }
        //all players ready
        populateBank();
        populateMarket();
        addStartMoney();
        determineStarter();
        //set game to started

        if (players.size() >= 2) {
            this.started = true;
        } else {
            throw new AlhambraGameRuleException("Get some friends!");
        }
    }

    public void endGame() {
        //calculate score 3
        //set game to ended
        if (!started) {
            throw new AlhambraGameRuleException("The game hasn't even started yet");
        }
        this.ended = true;
    }

    private void setCurrentPlayer() {
        //next player of List
    }

    public void addStartMoney(){
        //each player to > 20 coins

        for (Player p : players) {
            while(p.getMoney().getTotalAmount() < 20){
                p.getMoney().addCoin(coinStack.poll());
            }
        }

    }  

    public void determineStarter() {
        //get player with minimum cards
        //if equal, get player with min value
        //if equal, take highest in list
        int smallestNumberOfCards = 20;
        int totalAmountOfCoins = 0;
        for (Player p : players) {
            int numberOfCoins = p.getMoney().getCoins().size();
            if (numberOfCoins < smallestNumberOfCards) {
                smallestNumberOfCards = numberOfCoins;
                totalAmountOfCoins = p.getMoney().getTotalAmount();
                currentPlayer = p;
            }else if (numberOfCoins == smallestNumberOfCards && totalAmountOfCoins > p.getMoney().getTotalAmount()){
                    currentPlayer = p;
                    totalAmountOfCoins = p.getMoney().getTotalAmount();

            }

      
        }

    }

    public void setCurrentPlayer(Player currentPlayer) {
        int indexOfCurrentPlayer = players.indexOf(currentPlayer);
        if (indexOfCurrentPlayer == players.size() - 1) {
            this.currentPlayer = players.get(0);
        } else {
            this.currentPlayer = players.get(indexOfCurrentPlayer + 1);
        }

    }

}