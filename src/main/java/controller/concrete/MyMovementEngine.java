package controller.concrete;

import controller.*;
import controller.command.ShipCommandProvider;
import controller.command.concrete.MyShipShipCommandProvider;
import controller.visitor.GameState;
import javafx.scene.input.KeyCode;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;


public class MyMovementEngine implements MovementEngine {
    private final GameState gameState;
    private final EntityControllerProvider entityControllers;
    private final ShipCommandProvider shipCommandProvider;

    public MyMovementEngine(@NotNull GameState gameState) {
        this.gameState=gameState;
        gameState.visit(this);
        entityControllers=new MyEntityControllerProvider();
        shipCommandProvider =new MyShipShipCommandProvider();
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        for (Projectile projectile : gameState.getProjectiles()) {
            entityControllers.get(projectile).move(projectile,secondsSinceLastFrame);
        }
        for (Ship ship : gameState.getShips()) {
            entityControllers.get(ship).move(ship,secondsSinceLastFrame);
        }
        for (Asteroid asteroid : gameState.getAsteroids()) {
            entityControllers.get(asteroid).move(asteroid,secondsSinceLastFrame);
        }
    }

    @Override
    public void keyPressed(KeyCode keyCode) {

    }

    public void updateUserMovableEntity(Ship ship, Movement movement, double secondsSinceLastFrame){
        shipCommandProvider.getCommand(movement).execute(ship,entityControllers.get(ship),secondsSinceLastFrame);
    }

    @Override
    public void added(Ship ship) {}

    @Override
    public void added(Asteroid asteroid) {}

    @Override
    public void added(Projectile projectile) {}

    @Override
    public void removed(Ship ship) {}

    @Override
    public void removed(Asteroid asteroid) {}

    @Override
    public void removed(Projectile projectile) {}
}
