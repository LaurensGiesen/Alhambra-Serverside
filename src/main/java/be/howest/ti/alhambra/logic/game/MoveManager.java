package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.gamebord.Player;

public class MoveManager {

    private boolean canPlay(Game game, Player player ){
        if (game.getCurrentPlayer() == game.getPlayerByName(player.getPlayerName())) {
            return true;
        }else{
            throw  new AlhambraGameRuleException("Stop cheating");
        }
    }

    public boolean canTakeMoney(Game game, Player player){
        canPlay(game, player);

        if (game.getPlayerByName(player.getPlayerName()) == null) {
            throw new AlhambraEntityNotFoundException("No such player in the game");
        }
        if (game.getPlayerByName(player.getPlayerName()) != game.getCurrentPlayer()) {
            throw new AlhambraGameRuleException("Not the current player");
        }
        for (Coin coin : game.getBank().getCoins()) {
            if (!game.getBank().getCoins().contains(coin)) {
                throw new AlhambraEntityNotFoundException("No such coin in Bank");
            }
        }
        if (player.getMoney().getNumberOfCoins() > 1 && player.getMoney().getTotalAmount() > 5) {
            throw new AlhambraGameRuleException("2 coins with a value higher as 5");
        }

            return true;

    }

    public void takeMoney(Game game, Player player, Purse coins) {
        for (Coin coin : coins.getCoins()) {
            game.getBank().removeCoin(coin);
            game.getPlayerByName(player.getPlayerName()).getMoney().addCoin(coin);
        }
    }

}
