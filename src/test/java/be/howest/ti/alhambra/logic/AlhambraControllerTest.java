package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlhambraControllerTest {

    AlhambraController controller;

    @BeforeEach
    void createController(){
        controller = new AlhambraController();
    }

    @Test
    void createGame() {
        String gameId = controller.createGame();
        assertEquals(1, controller.getServer().getGames().size());
    }
}