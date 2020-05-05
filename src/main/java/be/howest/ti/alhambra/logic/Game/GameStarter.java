package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;

import java.util.*;

public class GameStarter {

    private GameStarter() {
    }

    public static boolean isReadyToStart(Game game) {
        if (game.getPlayers().size() < 2) {
            return false;
        }
        for (Player p : game.getPlayers()) {
            if (!p.isReady()) {
                return false;
            }
        }
        return true;
    }

    public static void startGame(Game game) {
        if (game.getPlayers().size() < 2) {
            throw new AlhambraGameRuleException("Not enough players to start");
        }
        for (Player p : game.getPlayers()) {
            if (!p.isReady()) {
                throw new AlhambraGameRuleException("Not all are ready");
            }
        }

        Populator.populateCoinStack(game.getCoinStack());
        Populator.populateBuildingStack(game.getBuildingStack());

        game.setStarted(true);

        for (Player p : game.getPlayers()) {
            Populator.giveStartMoney(p.getMoney(), game.getCoinStack());
        }

        game.getPlayers().sort(new OrderByPurse());
        game.setCurrentPlayer(game.getPlayers().get(0));

        Populator.populateBank(game.getCoinStack(), game.getBank());
        Populator.populateMarket(game.getBuildingStack(), game.getMarket());
    }

}
