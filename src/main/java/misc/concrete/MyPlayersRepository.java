package misc.concrete;

import controller.visitor.GameState;
import misc.Player;
import misc.PlayersRepository;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPlayersRepository implements PlayersRepository {
    private final GameState gameState;
    private final Map<Integer, Player> playersIDMap;
    private int lastPlayerID;

    public MyPlayersRepository(@NotNull GameState gameState) {
        playersIDMap=new HashMap<>();
        this.gameState=gameState;
        lastPlayerID=0;
    }

    public void addPlayer(Player player){
        gameState.getPlayers().add(player);
        playersIDMap.put(player.getId(),player);
        lastPlayerID=player.getId();
    }

    @Override
    public void addPlayers(List<Player> players) {
        for (Player player : players) {
            playersIDMap.put(player.getId(),player);
            lastPlayerID=player.getId();
        }
    }

    public int getNewPlayerId(){
        return ++lastPlayerID;
    }

    public void addPointsToPlayer(int playerID, int points){
        if (playersIDMap.containsKey(playerID)){
            playersIDMap.get(playerID).addPoints(points);
        }
    }

    public List<Player> getPlayers() {
        return gameState.getPlayers();
    }
}
