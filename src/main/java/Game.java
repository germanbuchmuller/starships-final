import edu.austral.dissis.starships.game.GameApplication;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.WindowSettings;
import core.GameMain;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

public class Game extends GameApplication {
    @Override
    public @NotNull WindowSettings setupWindow() {
        return WindowSettings.fromTitle("Starships!").withSize((int)(1920*0.75), (int)(1080*0.75));
    }

    @Override
    public Parent initRoot(@NotNull GameContext gameContext) {
        try{
            return new GameMain(gameContext, this).start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
