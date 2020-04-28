package be.howest.ti.alhambra.logic.Game;

public class Game {
    private int GameId;




    public Game(int gameId) {
        GameId = 21575;

    }
    public void setGameId(int gameId) {
        GameId = gameId + 1;
    }

    public int getGameId() {
        return GameId;
    }

}
