package be.howest.ti.alhambra.logic.Game;

public class Game {

    private static int numberOfGames = 0;
    private int gameId;



    private boolean started;
    private boolean ended;

    public Game(boolean started, boolean ended) {
        this.started = started;
        this.ended = ended;
        gameId = numberOfGames + 21575;
        numberOfGames ++;
    }
    public String getTheStateOfTheGame(){
        return "started =" + started + ",ended =" + ended;

    }
}
