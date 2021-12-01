package engine;

import controller.visitor.GameState;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GameSaver {
    GameState initialize();
    void saveGame(@NotNull GameState gameState) throws Exception;
    void loadGame() throws Exception;
}
