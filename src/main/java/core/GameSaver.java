package core;

import controller.visitor.GameState;
import org.jetbrains.annotations.NotNull;


public interface GameSaver {
    GameState initialize();
    void saveGame(@NotNull GameState gameState) throws Exception;
    void loadGame() throws Exception;
}
