package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.Game.Game;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Currency;
import be.howest.ti.alhambra.logic.coin.Purse;


import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;


public class TakeMoneyTestOfGame {
    Coin coin1 = new Coin(Currency.GREEN, 1);
    Coin coin2 = new Coin(Currency.BLUE, 3);
    Coin coin3 = new Coin(Currency.YELLOW, 6);
    Game game;

    public void createGame() {
        game = new Game();
        game.addPlayer("Laurens");
        game.addPlayer("piet");
        game.setCurrentPlayer(game.getPlayerByName("Laurens"));
    }

    @Test
    void testOfExceptionWhenPlayerIsZero() {
        createGame();

        assertThrows(AlhambraEntityNotFoundException.class, () -> game.takeMoney(null, new Purse()));
    }

    @Test
    void testWhenPlayerIsNotCurrentPlayer() {
        createGame();
        assertThrows(AlhambraGameRuleException.class, () -> game.takeMoney("Laurens", new Purse()));
    }

    @Test
    void testWhenCoinIsNotInTheBank() {
        createGame();
        Purse p = new Purse();
        p.addCoin(new Coin(Currency.BLUE, 7));
        assertThrows(AlhambraEntityNotFoundException.class, () -> game.takeMoney("piet", p));
    }

    @Test
    void testWhenTotalCoinsAmountIsAbove5() {
        createGame();
        game.getBank().addCoin(coin2);
        game.getBank().addCoin(coin3);
        Purse p = new Purse();
        p.addCoin(coin2);
        p.addCoin(coin3);
        assertThrows(AlhambraGameRuleException.class, () -> game.takeMoney("piet", p));
    }

    @Test
    void testOfNormalBehaviourOfOneCoin() {
        createGame();
        game.getBank().addCoin(coin2);
        Purse p = new Purse();
        p.addCoin(coin2);
        game.takeMoney("piet", p);
        List<Coin> pietsPurse = game.getPlayerByName("piet").getMoney().getCoins();
        assertTrue(pietsPurse.contains(coin2));
    }

    @Test
    void testOfNormalBehaviourOfTwoCoins() {
        createGame();
        game.getBank().addCoin(coin2);
        game.getBank().addCoin(coin1);
        Purse p = new Purse();
        p.addCoin(coin1);
        p.addCoin(coin2);
        game.takeMoney("piet", p);
        List<Coin> pietsPurse = game.getPlayerByName("piet").getMoney().getCoins();
        assertTrue(pietsPurse.contains(coin2) || pietsPurse.contains(coin1));
    }
}