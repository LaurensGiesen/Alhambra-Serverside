package be.howest.ti.alhambra.logic.Game;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;
    private boolean started;
    private boolean ended;

    public Game() {
        gameId = numberOfGames + 21575;
        numberOfGames ++;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isEnded() {
        return ended;
    }


    public void startGame(){
        this.started = true;
    }

    public void endGame(){
        this.ended = true;
    }
}





