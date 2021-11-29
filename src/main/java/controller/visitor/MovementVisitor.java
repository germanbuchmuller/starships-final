package controller.visitor;

import controller.concrete.MyShipController;
import javafx.scene.layout.Pane;
import model.concrete.Asteroid;
import model.Entity;
import model.concrete.Projectile;
import model.concrete.Ship;

import java.util.ArrayList;
import java.util.List;

public class MovementVisitor implements EntityVisitor {
    private final List<Ship> ships;
    private final List<Entity> selfMovableEntities;
    private final List<Entity> entitiesToRemove;
    MyShipController myShipController;
    private final Pane pane;

    public MovementVisitor(Pane pane) {
        selfMovableEntities =new ArrayList<>();
        entitiesToRemove =new ArrayList<>();
        ships =new ArrayList<>();
        this.pane=pane;

        //myShipController =new MyShipController(pane);
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
        /*
        myShipController.setCanMoveOutOfBounds(true);
        for (Entity selfMovableEntity : selfMovableEntities) {
            myShipController.moveForward(selfMovableEntity, secondsSinceLastFrame);
            if (checkIfEntityOutOfBorders(selfMovableEntity)){
                selfMovableEntity.destroy();
                entitiesToRemove.add(selfMovableEntity);
            }
        }
        selfMovableEntities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
        updateShips(secondsSinceLastFrame);
        */
    }

    public void updateShips(double secondsSinceLastFrame){
        /*
        myShipController.setCanMoveOutOfBounds(false);
        for (Ship ship : ships) {
            myShipController.slowDown(ship,secondsSinceLastFrame);
        }

         */
    }
/*
    public void updateUserMovableEntity(Entity entity, Movement movement, double secondsSinceLastFrame){
        myShipController.setCanMoveOutOfBounds(false);
        checkIfEntityOutOfBorders(entity);
        switch (movement){
            case FORWARD:
                myShipController.moveForward(entity,secondsSinceLastFrame);
                break;
            case BACKWARDS:
                myShipController.moveBackwards(entity,secondsSinceLastFrame);
                break;
            case LEFT:
                myShipController.moveLeft(entity,secondsSinceLastFrame);
                break;
            case RIGHT:
                myShipController.moveRight(entity,secondsSinceLastFrame);
                break;
            case ROTATE_LEFT:
                myShipController.rotateLeft(entity,secondsSinceLastFrame);
                break;
            case ROTATE_RIGHT:
                myShipController.rotateRight(entity,secondsSinceLastFrame);
                break;
            case SHOOT:
                myShipController.shoot(entity);
                break;
        }
    }

 */

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
