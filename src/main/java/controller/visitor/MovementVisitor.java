package controller.visitor;

import controller.EntityController;
import controller.Movement;
import javafx.scene.layout.Pane;
import model.Asteroid;
import model.Entity;
import model.Projectile;
import model.Ship;

import java.util.ArrayList;
import java.util.List;

public class MovementVisitor implements EntityVisitor {
    private final List<Ship> ships;
    private final List<Entity> selfMovableEntities;
    private final List<Entity> entitiesToRemove;
    EntityController entityController;
    private final Pane pane;

    public MovementVisitor(Pane pane) {
        selfMovableEntities =new ArrayList<>();
        entitiesToRemove =new ArrayList<>();
        ships =new ArrayList<>();
        this.pane=pane;

        entityController=new EntityController(pane);
    }

    public List<Entity> getSelfMovableEntities() {
        return selfMovableEntities;
    }

    @Override
    public void visit(Ship ship){
        ships.add(ship);
    }

    @Override
    public void visit(Asteroid asteroid) {
        selfMovableEntities.add(asteroid);
    }

    @Override
    public void visit(Projectile projectile) {
        selfMovableEntities.add(projectile);
    }

    public void updateSelfMovableEntities(double secondsSinceLastFrame){
        entityController.setCanMoveOutOfBounds(true);
        for (Entity selfMovableEntity : selfMovableEntities) {
            entityController.moveForward(selfMovableEntity, secondsSinceLastFrame);
            if (checkIfEntityOutOfBorders(selfMovableEntity)){
                selfMovableEntity.destroy();
                entitiesToRemove.add(selfMovableEntity);
            }
        }
        selfMovableEntities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
        updateShips(secondsSinceLastFrame);
    }

    public void updateShips(double secondsSinceLastFrame){
        entityController.setCanMoveOutOfBounds(false);
        for (Ship ship : ships) {
            entityController.slowDown(ship,secondsSinceLastFrame);
        }
    }

    public void updateUserMovableEntity(Entity entity, Movement movement, double secondsSinceLastFrame){
        entityController.setCanMoveOutOfBounds(false);
        checkIfEntityOutOfBorders(entity);
        switch (movement){
            case FORWARD:
                entityController.moveForward(entity,secondsSinceLastFrame);
                break;
            case BACKWARDS:
                entityController.moveBackwards(entity,secondsSinceLastFrame);
                break;
            case LEFT:
                entityController.moveLeft(entity,secondsSinceLastFrame);
                break;
            case RIGHT:
                entityController.moveRight(entity,secondsSinceLastFrame);
                break;
            case ROTATE_LEFT:
                entityController.rotateLeft(entity,secondsSinceLastFrame);
                break;
            case ROTATE_RIGHT:
                entityController.rotateRight(entity,secondsSinceLastFrame);
                break;
            case SHOOT:
                entityController.shoot(entity);
                break;
        }
    }

    private boolean checkIfEntityOutOfBorders(Entity entity){
        if (pane.getLayoutBounds().getMaxY()<(entity.getY()-entity.getHeight()-entity.getWidth()) || pane.getLayoutBounds().getMaxX()<(entity.getX()-entity.getWidth()-entity.getHeight())){
            return true;
        }else return entity.getY() + entity.getHeight() + entity.getWidth() < 0 || entity.getX() + entity.getHeight() + entity.getWidth() < 0;
    }

    private boolean checkIfEntityCollidingWithBorders(Entity entity){
        if (pane.getLayoutBounds().getMaxY()<=(entity.getY()+entity.getHeight()) || pane.getLayoutBounds().getMaxX()<=(entity.getX()+entity.getWidth())){
            return true;
        }else return entity.getY()  <= 0 || entity.getX()  <= 0;
    }

}
