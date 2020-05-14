package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;

import java.util.*;

public class Server {

    /* ------------ FIELDS ------------ */
    private Set<Game> games;
    private String adminToken = "ALHAMBRA1ADMIN1TOKEN";


    /* ------------ CONSTRUCTOR ------------ */
    public Server() {
        games = new HashSet<>();
    }


    /* ------------ PUBLIC METHODS ------------ */

    public boolean isValidAdminToken(String adminToken) {
        return this.adminToken.equals(adminToken);
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

    public Set<Game> getNotStartedGames() {
        Set<Game> notStartedGames = new HashSet<>();
        for(Game game : games){
            if(!game.isStarted()){
                notStartedGames.add(game);
            }
        }
        return notStartedGames;
    }

}
