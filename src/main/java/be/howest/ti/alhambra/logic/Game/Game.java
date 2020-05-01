package be.howest.ti.alhambra.logic.Game;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;


    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++; 
    }
}
