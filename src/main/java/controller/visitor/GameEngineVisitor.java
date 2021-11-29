package controller.visitor;

import controller.MovementEngine;
import controller.collision.CollisionsEngine;
import engine.GameEngine;
import view.RenderEngine;

public interface GameEngineVisitor extends EntityVisitor {
    void visit(GameEngine gameEngine);
    void visit(RenderEngine renderEngine);
    void visit(MovementEngine movementEngine);
    void visit(CollisionsEngine collisionsEngine);
}
