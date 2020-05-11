package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;

public class TurnManager {

    private TurnManager() {
    }

    public static boolean isReadyToStart(Game game) {
        return !game.isStarted() && game.getPlayerCount() >= 2 && game.getPlayerCount() == game.getReadyCount();
    }

    public static void startGame(Game game) {
        Populator.populateBank(game.getCoinStack(), game.getBank());
        Populator.populateMarket(game.getBuildingStack(), game.getMarket());
        for (Player p : game.getPlayers()) {
            Populator.giveStartMoney(game.getCoinStack(), p.getMoney());
        }
        determineStarter(game);
        game.setStarted(true);
    }

    public static void endTurn(Game game) {
        Populator.populateBank(game.getCoinStack(), game.getBank());
        if(!Populator.populateMarket(game.getBuildingStack(), game.getMarket())) {
            TurnManager.endGame(game);
        }
        setNextPlayer(game);
    }

    public static void endGame(Game game) {
        //calculate score 3
        //set game to ended
        if (!game.isStarted()) {
            throw new AlhambraGameRuleException("The game hasn't even started yet");
        } else{
            ScoreCalculator.score(game.getPlayers(), 3);
            game.setEnded(true);
        }
    }

    private static void determineStarter(Game game) {
        //get player with minimum cards
        //if equal, get player with min value
        //if equal, take highest in list
        int smallestNumberOfCards = 20;
        int totalAmountOfCoins = 0;
        for (Player p : game.getPlayers()) {
            int numberOfCoins = p.getMoney().getCoins().size();
            if (numberOfCoins < smallestNumberOfCards) {
                smallestNumberOfCards = numberOfCoins;
                totalAmountOfCoins = p.getMoney().getTotalAmount();
                game.setCurrentPlayer(p);
            } else if (numberOfCoins == smallestNumberOfCards && totalAmountOfCoins > p.getMoney().getTotalAmount()) {
                game.setCurrentPlayer(p);
                totalAmountOfCoins = p.getMoney().getTotalAmount();
            }
        }
    }

    private static void setNextPlayer(Game game) {
        Player currPlayer = game.getCurrentPlayer();
        int posCurrPlayer = game.getPlayers().indexOf(currPlayer);

        if (posCurrPlayer == game.getPlayers().size() - 1) {
            game.setCurrentPlayer(game.getPlayers().get(0));
        } else {
            game.setCurrentPlayer(game.getPlayers().get(posCurrPlayer + 1));
        }
    }

}
