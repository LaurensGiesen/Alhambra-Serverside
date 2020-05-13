package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Buildingtype;
import be.howest.ti.alhambra.logic.gamebord.Player;
import be.howest.ti.alhambra.logic.sort.*;

import java.util.ArrayList;
import java.util.List;


public class ScoreCalculator {

    private static Scorecard scorecard = new Scorecard();

    private ScoreCalculator() {}

    public static void score(List<Player> players, int round){
        scoreBuildingTypes(players, round);
        scoreWalls(players);
    }

    private static void scoreBuildingTypes(List<Player> players, int round) {
        for(Buildingtype buildingtype : Buildingtype.values()){
            List<List<Player>> playerTable = playersWithMostBuildings(players, buildingtype);
            for(int position = 1; position < playerTable.size()+1; position++){
                int score = 0;
                if(!playerTable.get(position-1).isEmpty()){
                    score = getScoreForBuildingType(round, buildingtype, playerTable, position, score);
                }

                for(int i = 0; i < playerTable.get(position-1).size(); i++){
                    playerTable.get(position-1).get(i).addScore(score);
                }
            }
        }
    }
    private static void scoreWalls(List<Player> players) {
        List<Player> playerWithLongestWall = new ArrayList<>();
        int maxLength = 0;

        for(Player p : players){
            int lengthWall = p.getCity().getLengthWall();
            if(lengthWall > maxLength){
                playerWithLongestWall = new ArrayList<>();
                playerWithLongestWall.add(p);
                maxLength = lengthWall;
            } else if (lengthWall == maxLength){
                playerWithLongestWall.add(p);
            }
        }

        for(Player p : playerWithLongestWall){
            p.addScore(maxLength/playerWithLongestWall.size());
        }

    }

    private static int getScoreForBuildingType(int round, Buildingtype buildingtype, List<List<Player>> playerTable, int position, int score) {
        score += scorecard.getScore(buildingtype, round, position);
        if(position < playerTable.size() && playerTable.get(position-1).size() > 1){
            score += scorecard.getScore(buildingtype, round, position+1);
        }
        if(position+1 < playerTable.size() && playerTable.get(position-1).size() > 2){
            score += scorecard.getScore(buildingtype, round, position+2);
        }
        score /=  playerTable.get(position-1).size();
        return score;
    }

    private static List<List<Player>> playersWithMostBuildings(List<Player> players, Buildingtype buildingtype){
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

}