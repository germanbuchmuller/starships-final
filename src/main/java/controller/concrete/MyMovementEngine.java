package controller.concrete;

import controller.*;
import controller.visitor.GameState;
import javafx.scene.input.KeyCode;
import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MyMovementEngine implements MovementEngine {
    private final GameState gameState;
    private final Map<Entity, EntityController> entityControllers;

    public MyMovementEngine(@NotNull GameState gameState) {
        this.gameState=gameState;
        gameState.visit(this);
        entityControllers=new HashMap<>();
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        for (Entity entity : gameState.getEntities()) {
            if (entityControllers.containsKey(entity)){
                entityControllers.get(entity).move(secondsSinceLastFrame);
            }
        }
    }

    @Override
    public void keyPressed(KeyCode keyCode) {

    }

    public void updateUserMovableEntity(Ship ship, Movement movement, double secondsSinceLastFrame){
        ShipController shipController = (ShipController) entityControllers.get(ship);
        switch (movement){
            case FORWARD:
                shipController.moveForward(secondsSinceLastFrame);
                break;
            case BACKWARDS:
                shipController.moveBackwards(secondsSinceLastFrame);
                break;
            case LEFT:
                //shipController.moveLeft(entity,secondsSinceLastFrame);
                break;
            case RIGHT:
                //shipController.moveRight(entity,secondsSinceLastFrame);
                break;
            case ROTATE_LEFT:
                shipController.rotateLeft(secondsSinceLastFrame);
                break;
            case ROTATE_RIGHT:
                shipController.rotateRight(secondsSinceLastFrame);
                break;
            case SHOOT:
                shipController.shoot(secondsSinceLastFrame);
                break;
        }
    }

    @Override
    public void add(Ship ship) {
        entityControllers.put(ship,new MyShipController(ship));
    }

    @Override
    public void add(Asteroid asteroid) {

    }

    @Override
    public void add(Projectile projectile) {
        entityControllers.put(projectile,new MyProjectileController(projectile));
    }

    @Override
    public void remove(Ship ship) {
        entityControllers.remove(ship);
    }

    @Override
    public void remove(Asteroid asteroid) {
        entityControllers.remove(asteroid);
    }

    @Override
    public void remove(Projectile projectile) {
        entityControllers.remove(projectile);
    }
}
