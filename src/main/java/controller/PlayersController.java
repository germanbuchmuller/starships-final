package controller;

import misc.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersController {
    private List<Player> playersList;
    private Map<Integer, Player> playersIDMap;
    private int lastPlayerID;

    public PlayersController() {
        playersIDMap=new HashMap<>();
        playersList=new ArrayList<>();
        lastPlayerID=0;
    }

    public void addPlayer(Player player){
        if (!playersList.contains(player)){
            playersList.add(player);
            playersIDMap.put(player.getId(),player);
            lastPlayerID=player.getId();
        }
    }

    public int getNewPlayerId(){
        return ++lastPlayerID;
    }

    public void addPointsToPlayer(int playerID, int points){
        if (playersIDMap.containsKey(playerID)) playersIDMap.get(playerID).addPoints(points);
    }

    public List<Player> getPlayers() {
        return playersList;
    }
}
