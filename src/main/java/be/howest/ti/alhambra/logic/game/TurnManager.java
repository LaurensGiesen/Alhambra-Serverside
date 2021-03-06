package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

public class TurnManager {

    /* ------------ CONSTRUCTOR ------------ */
    private TurnManager() {
    }

    /* ------------ PUBLIC METHODS ------------ */
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

        endOfTurnScore(game, 1);
        endOfTurnScore(game, 2);

        if (!Populator.populateMarket(game.getBuildingStack(), game.getMarket())) {
            TurnManager.endGame(game);
        }
        setNextPlayer(game);
    }

    public static void endGame(Game game) {
        //calculate score 3
        //set game to ended
        if (!game.isStarted()) {
            throw new AlhambraGameRuleException("The game hasn't even started yet");
        } else {
            ScoreCalculator.score(game.getPlayers(), 3);
            game.setEnded(true);
        }
    }

    /* ------------ PRIVATE METHODS ------------ */
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
                game.setCurrentPlayer(p.getPlayerName());
            } else if (numberOfCoins == smallestNumberOfCards && totalAmountOfCoins > p.getMoney().getTotalAmount()) {
                game.setCurrentPlayer(p.getPlayerName());
                totalAmountOfCoins = p.getMoney().getTotalAmount();
            }
        }
    }

    private static void setNextPlayer(Game game) {
        Player currPlayer = game.getPlayerByName(game.getCurrentPlayer());
        int posCurrPlayer = game.getPlayers().indexOf(currPlayer);

        if (posCurrPlayer == game.getPlayers().size() - 1) {
            game.setCurrentPlayer(game.getPlayers().get(0).getPlayerName());
        } else {
            game.setCurrentPlayer(game.getPlayers().get(posCurrPlayer + 1).getPlayerName());
        }
    }

    private static void endOfTurnScore(Game game, int round) {
        if (game.getCoinStack().size() < game.getScoringRound()[round-1]) {
            ScoreCalculator.score(game.getPlayers(), round);
            game.getScoringRound()[round-1] = -1;
        }
    }

}
