package controller;

import edu.austral.dissis.starships.vector.Vector2;
import model.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityController {

    public void moveForward(Entity entity, double secondsSinceLastFrame) {
        Vector2 vector = Vector2.vectorFromModule(-entity.getMaxSpeed()*secondsSinceLastFrame,Math.toRadians(entity.getAngle())+Math.toRadians(90));
        move(entity, vector);
    }

    public void moveBackwards(Entity entity, double secondsSinceLastFrame) {
        Vector2 vector = Vector2.vectorFromModule(entity.getMaxSpeed()*secondsSinceLastFrame,Math.toRadians(entity.getAngle())+Math.toRadians(90));
        move(entity, vector);
    }

    public void moveLeft(Entity entity, double secondsSinceLastFrame) {
        Vector2 vector = Vector2.vectorFromModule(-entity.getMaxSpeed()*secondsSinceLastFrame,Math.toRadians(entity.getAngle())+Math.toRadians(0));
        move(entity, vector);
    }

    public void moveRight(Entity entity, double secondsSinceLastFrame) {
        Vector2 vector = Vector2.vectorFromModule(-entity.getMaxSpeed()*secondsSinceLastFrame,Math.toRadians(entity.getAngle())+Math.toRadians(180));
        move(entity, vector);
    }

    private void move(Entity entity, Vector2 v){
        entity.setPosition(entity.getX()+v.getX(), entity.getY()+v.getY(), entity.getAngle());
    }

    public void rotateLeft(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()+entity.getMaxSpeed()*secondsSinceLastFrame);
    }

    public void rotateRight(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()-entity.getMaxSpeed()*secondsSinceLastFrame);
    }
}
