package be.howest.ti.alhambra.logic.Game;

import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.gamebord.Player;
import be.howest.ti.alhambra.logic.sort.*;

import java.util.ArrayList;
import java.util.List;


public class ScoreCalculator {

    private static Scorecard scorecard = new Scorecard();

    private ScoreCalculator() {}

    public static void score(List<Player> players, int round){
        for(Buildingtype buildingtype : Buildingtype.values()){
            List<List<Player>> playerTable = playersWithMostBuildings(players, buildingtype);
            for(int position = 1; position < playerTable.size()+1; position++){
                int score = 0;
                if(!playerTable.get(position-1).isEmpty()){
                    score = scorecard.getScore(buildingtype, round, position) / playerTable.get(position-1).size();
                }
                for(int i = 0; i < playerTable.get(position-1).size(); i++){
                    playerTable.get(position-1).get(i).addScore(score);
                }
            }
        }
    }

    public static List<List<Player>> playersWithMostBuildings(List<Player> players, Buildingtype buildingtype){
        List<Player> playersCopy = new ArrayList<>(List.copyOf(players));

        switch(buildingtype){
            case PAVILION:
                playersCopy.sort(new SortPlayerByPavilion());
                break;
            case SERAGLIO:
                playersCopy.sort(new SortPlayerBySeraglio());
                break;
            case ARCADES:
                playersCopy.sort(new SortPlayerByArcade());
                break;
            case CHAMBERS:
                playersCopy.sort(new SortPlayerByChamber());
                break;
            case GARDEN:
                playersCopy.sort(new SortPlayerByGarden());
                break;
            case TOWER:
                playersCopy.sort(new SortPlayerByTower());
                break;
        }

        List<List<Player>> results = new ArrayList<>();
        results.add(new ArrayList<>());
        results.add(new ArrayList<>());
        results.add(new ArrayList<>());

        int prevAmount = 0;
        int prevPosition = 0;
        int position = 0;
        for(Player p : playersCopy){
            int amount = p.getCity().getAmountOfBuildings(buildingtype);
            if(prevPosition < 3 && amount > 0){
                if(amount == prevAmount){
                    results.get(prevPosition).add(p);
                } else {
                    results.get(position).add(p);
                    prevPosition = position;
                }
            }
            prevAmount = amount;
            position++;
        }

        return results;
    }









//    public void determineScoreRound1(List<Player> players) {
//        loadPlayerStats(players);
//
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.PAVILION), 1, 0, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.SERAGLIO), 2, 0, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.ARCADES), 3, 0, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.CHAMBERS), 4, 0, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.GARDEN), 5, 0, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.TOWER), 6, 0, 0);
//    }
//
//
//    public void determineScoreRound2(List<Player> players) {
//        loadPlayerStats(players);
//
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.PAVILION), 8, 1, 0 );
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.SERAGLIO), 9, 2, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.ARCADES), 10, 3, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.CHAMBERS), 11, 4, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.GARDEN), 12, 5, 0);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.TOWER), 13, 6, 0);
//    }
//
//    public void determineScoreRound3(List<Player> players) {
//        loadPlayerStats(players);
//
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.PAVILION), 16, 8, 1 );
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.SERAGLIO), 17, 9, 2);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.ARCADES), 18, 10, 3);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.CHAMBERS), 19, 11, 4);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.GARDEN), 20, 12, 5);
//        addScoreToPlayerRound(playerWithMostBuildings(players, Buildingtype.TOWER), 21, 13, 6);
//    }
//
//
//    private static List<List<Player>> playerWithMostBuildings (List < Player > players, Buildingtype buildingtype){
//        List<List<Player>> outputList = new ArrayList<>();
//        List<Player> firstPlayers = new ArrayList<>();
//        List<Player> secondPlayers = new ArrayList<>();
//        List<Player> thirdPlayers = new ArrayList<>();
//
//        players.forEach(p -> p.setAmountOfBuildingType(p.getCity().getAmountOfBuildings(buildingtype)));
//
//        players.sort(new SortByAmount());
//        Player p1 = players.get(0);
//        Player p2 = players.get(1);
//        Player p3 = players.get(2);
//
//        firstPlayers.add(p1);
//
//        if (p1.getAmountOfBuildingType() == p3.getAmountOfBuildingType()) {
//            firstPlayers.add(p2);
//            firstPlayers.add(p3);
//        } else {
//            if (p1.getAmountOfBuildingType() == p2.getAmountOfBuildingType()) {
//                firstPlayers.add(p2);
//                thirdPlayers.add(p3);
//            }
//            if (p2.getAmountOfBuildingType() == p3.getAmountOfBuildingType()) {
//                secondPlayers.add(p2);
//                secondPlayers.add(p3);
//            }
//        }
//        outputList.add(firstPlayers);
//        outputList.add(secondPlayers);
//        outputList.add(thirdPlayers);
//
//        return outputList;
//    }
//
//    private void addScoreToPlayerRound(List<List<Player>> players, int amount1, int amount2, int amount3) {
//
//        List<Player> firstPlayers = players.get(0);
//        List<Player> secondPlayers = players.get(1);
//        List<Player> thirdPlayers = players.get(2);
//
//        if (firstPlayers.size() == 3){
//            int amount = amount1 + amount2 + amount3;
//            firstPlayers.forEach(player -> player.addScore(amount / 3));
//        }
//        if (firstPlayers.size() == 2){
//            int amount = amount1 + amount2;
//            firstPlayers.forEach(player -> player.addScore(amount / 2));
//
//            thirdPlayers.forEach(player -> player.addScore(amount3));
//        }else{
//            firstPlayers.forEach(player -> player.addScore(amount1));
//
//            if(secondPlayers.size() == 2){
//                int amount = amount2 + amount3;
//                secondPlayers.forEach(player -> player.addScore(amount / 2));
//            }else{
//                secondPlayers.forEach(player -> player.addScore(amount2));
//                thirdPlayers.forEach(player -> player.addScore(amount3));
//            }
//        }
//    }
//
//    private void loadPlayerStats(List<Player> players) {
//        playerWithMostBuildings(players, Buildingtype.PAVILION);
//        playerWithMostBuildings(players, Buildingtype.SERAGLIO);
//        playerWithMostBuildings(players, Buildingtype.ARCADES);
//        playerWithMostBuildings(players, Buildingtype.CHAMBERS);
//        playerWithMostBuildings(players, Buildingtype.GARDEN);
//        playerWithMostBuildings(players, Buildingtype.TOWER);
//    }


}