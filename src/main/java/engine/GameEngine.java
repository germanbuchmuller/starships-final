package engine;

import controller.PlayersController;
import controller.visitor.CollisionsVisitor;
import controller.visitor.MovementVisitor;
import controller.visitor.RenderVisitor;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameEngine {
    private final CollisionsVisitor collisionsVisitor;
    private final MovementVisitor movementVisitor;
    private final RenderVisitor renderVisitor;
    private final PlayersController playersController;


    public GameEngine(Pane pane) {
        playersController=new PlayersController();
        movementVisitor=new MovementVisitor();
        renderVisitor=new RenderVisitor(pane);
        collisionsVisitor=new CollisionsVisitor(playersController);
    }

    public void update(double secondsSinceLastFrame) throws IOException {
        movementVisitor.updateSelfMovableEntities(secondsSinceLastFrame);
        collisionsVisitor.checkColisions();
        renderVisitor.update();
    }
}


