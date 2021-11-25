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
        //sacar factor comun de todos los vectors entre los move
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
        double x =entity.getX()+v.getX();
        double y =entity.getY()+v.getY();
        if (pane.getLayoutBounds().getMinX()<x && pane.getLayoutBounds().getMinY()<y && pane.getLayoutBounds().getMaxX()>(x+entity.getWidth()) && pane.getLayoutBounds().getMaxY() >(y+entity.getHeight())&&!canMoveOutOfBounds){
            entity.setPosition(x, y, entity.getAngle());
        }else if (canMoveOutOfBounds){
            entity.setPosition(x, y, entity.getAngle());
        }
    }

    public void rotateLeft(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()-entity.getMaxSpeed()*secondsSinceLastFrame);
    }

    public void rotateRight(Entity entity, double secondsSinceLastFrame){
        entity.setPosition(entity.getX(), entity.getY(), entity.getAngle()+entity.getMaxSpeed()*secondsSinceLastFrame);
    }

    public void shoot(Entity entity){
        if (entity.getType()== EntityType.SHIP){
            ((Ship)entity).shoot();
        }
    }
}
