package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

import java.util.*;

public class Server {

    /* ------------ FIELDS ------------ */
    private Set<Game> games;


    /* ------------ CONSTRUCTOR ------------ */
    public Server() {
        games = new HashSet<>();
    }


    /* ------------ PUBLIC METHODS ------------ */

    public boolean isValidAdminToken(String adminToken) {
        String adminToken1 = "ALHAMBRA1ADMIN1TOKEN";
        return adminToken1.equals(adminToken);
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

    public List<Integer> getNotStartedGameIds() {
        List<Integer> gameIds = new ArrayList<>();
        for (Game game : games) {
            if (!game.isStarted()) {
                gameIds.add(game.getGameId());
            }
        }
        return gameIds;
    }

}
