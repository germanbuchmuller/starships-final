import edu.austral.dissis.starships.game.GameApplication;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.WindowSettings;
import engine.GameEngine;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class Game extends GameApplication {
    @Override
    public @NotNull WindowSettings setupWindow() {
        return WindowSettings.fromTitle("Starships!").withSize(1920, 1080).withFullscreen(true);
    }

    @Override
    public Parent initRoot(@NotNull GameContext gameContext) {
        try{
            return new GameEngine(gameContext, this).start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}