package controller;

import edu.austral.dissis.starships.vector.Vector2;
import javafx.scene.layout.Pane;
import model.Entity;
import model.EntityType;
import model.Ship;

public class EntityController {
    private boolean canMoveOutOfBounds;
    private final Pane pane;

    public EntityController(Pane pane) {
        this.pane = pane;
    }

    public void setCanMoveOutOfBounds(boolean canMoveOutOfBounds) {
        this.canMoveOutOfBounds = canMoveOutOfBounds;
    }

    public void moveForward(Entity entity, double secondsSinceLastFrame) {
        move(entity,2.5*secondsSinceLastFrame,0);
    }

    public void moveBackwards(Entity entity, double secondsSinceLastFrame) {
        move(entity,2.5*secondsSinceLastFrame,180);
    }
    public void slowDown(Entity entity, double secondsSinsceLastFrame){
        if (!entity.isAccelerating()){
            Vector2 oppositeVector = Vector2.vectorFromModule(entity.getMovementDirection().getModule()*0.015,entity.getMovementDirection().getAngle()+Math.toRadians(180));
            entity.setMovementDirection(entity.getMovementDirection().add(oppositeVector));
            updatePosition(entity);
        }
        entity.setAccelerating(false);
    }

    public void moveLeft(Entity entity, double secondsSinceLastFrame) {
        move(entity,2.5*secondsSinceLastFrame,270);
    }

    public void moveRight(Entity entity, double secondsSinceLastFrame) {
        move(entity,2.5*secondsSinceLastFrame,90);
    }

    private void move(Entity entity, double amount, double direction){
        Vector2 newVector = entity.getMovementDirection().add(Vector2.vectorFromModule(amount,Math.toRadians(entity.getAngle())+Math.toRadians(direction+270)));
        if (newVector.getModule()>4){
            newVector=newVector.asUnitary().multiply(4);
        }
        entity.setMovementDirection(newVector);
        updatePosition(entity);
        entity.setAccelerating(true);
    }

    public void updatePosition(Entity entity){
        double x =entity.getX()+entity.getMovementDirection().getX();
        double y =entity.getY()+entity.getMovementDirection().getY();
        entity.setPosition(x, y, entity.getAngle());
    }

    public void rotateLeft(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()-250*secondsSinceLastFrame);
    }

    public void rotateRight(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()+250*secondsSinceLastFrame);
    }

    public void shoot(Entity entity){
        if (entity.getType()== EntityType.SHIP){
            ((Ship)entity).shoot();
        }
    }
}
