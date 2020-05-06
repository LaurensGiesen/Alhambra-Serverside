package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class Server {
    private Set<Game> games;

    public Server() {
        games = new HashSet<>();
    }

    public Set<Game> getGames() {
        return games;
    }

    public int newGame() {
        Game game = new Game();
        games.add(game);
        return game.getGameId();
    }

    public Game getGame(int gameId) {
        for (Game game : games) {
            if (game.getGameId() == gameId) {
                return game;
            }
        }
        throw new AlhambraEntityNotFoundException("Game not present");
    }

    public void resetGames() {
        games.clear();
    }

}