package be.howest.ti.alhambra.logic;


import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.MoveManager;
import be.howest.ti.alhambra.logic.gamebord.Player;

public class StartUp {
    public static void main(String[] args) {
        new StartUp().run();
    }

    private void run() {
        Player player = new Player("lau");
        System.out.println(player.getPlayerName());
    }
}

