package be.howest.ti.alhambra.logic.gamebord;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.building.Walling;
import be.howest.ti.alhambra.logic.coin.Coin;
import be.howest.ti.alhambra.logic.coin.Purse;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.MoveManager;
import be.howest.ti.alhambra.logic.game.TurnManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p1;
    Player p2;
    Game game;
    Building b1 = new Building(Buildingtype.TOWER, 7, new Walling(false,false,false,false));
    Building b2 = new Building(Buildingtype.ARCADES, 5, new Walling(false, false, false, false));

    Location l1;

    @BeforeEach
    public void createPlayer(){
        p1 = new Player("playerm");
        p2 = new Player("playerl");
        game = new Game();
        p1.getReserve().addBuilding(b1);
        p1.getCity().addBuilding(b2, new Location(-1, 0));
    }

    @Test
    void setReady() {
        assertFalse(p1.isReady());
        p1.setReady(true);
        assertTrue(p1.isReady());
        p1.setReady(false);
        assertFalse(p1.isReady());
    }

    @Test
    void isValidToken() {
        String token = p1.getToken();

        assertTrue(p1.isValidToken(token));
        assertFalse(p1.isValidToken(token + "aaa"));
        assertFalse(p1.isValidToken(null));
    }

    @Test
    void addScore() {
        assertEquals(0, p1.getScore());
        p1.addScore(50);
        assertEquals(50, p1.getScore());
        p1.addScore(20);
        assertEquals(70, p1.getScore());
        p1.addScore(0);
        assertEquals(70, p1.getScore());
        assertThrows(AlhambraGameRuleException.class, ()->p1.addScore(-50));
    }

    @Test
    void getMoneyAsArray() {
        p1.setReady(true);
        p2.setReady(true);

        TurnManager.startGame(game);

        Object object = p1.getMoney();
        Coin[] coin = p1.getMoneyAsArray();

        assertEquals(object, p1.getMoney());
        assertNotEquals(object, p1.getMoneyAsArray());
        assertArrayEquals(coin, p1.getMoneyAsArray());
    }

    @Test
    void getReserveAsArray() {
        game.addPlayer(p1.getPlayerName());
        game.addPlayer(p2.getPlayerName());

        p1.setReady(true);
        p2.setReady(true);

        TurnManager.startGame(game);

        Object object = p1.getReserve();
        Building[] building = p1.getReserveAsArray();

        assertEquals(object, p1.getReserve());
        assertNotEquals(object, p1.getReserveAsArray());
        assertArrayEquals(building, p1.getReserveAsArray());
    }
}
