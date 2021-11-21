import edu.austral.dissis.starships.game.GameApplication;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.WindowSettings;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

public class Game extends GameApplication {
    @Override
    public @NotNull WindowSettings setupWindow() {
        return WindowSettings.fromTitle("Starships!").withSize(1920, 1080).withFullscreen(true);
    }

    @Override
    public Parent initRoot(@NotNull GameContext gameContext) {
        return null;
    }
}
