package misc;

import java.util.List;

public interface PlayersRepository {
    void addPlayer(Player player);
    void addPlayers(List<Player> players);
    int getNewPlayerId();
    void addPointsToPlayer(int playerID, int points);
    List<Player> getPlayers();
}
