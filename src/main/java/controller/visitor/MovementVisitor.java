package controller.visitor;

import controller.EntityController;
import controller.Movement;
import model.Asteroid;
import model.Entity;
import model.Projectile;
import model.Ship;

import java.util.ArrayList;
import java.util.List;

public class MovementVisitor implements EntityVisitor {
    private final List<Entity> selfMovableEntities;
    EntityController entityController;

    public MovementVisitor() {
        selfMovableEntities =new ArrayList<>();
        entityController=new EntityController();
    }

    @Override
    public void visit(Ship ship){ }

    @Override
    public void visit(Asteroid asteroid) {
        selfMovableEntities.add(asteroid);
    }

    @Override
    public void visit(Projectile projectile) {
        selfMovableEntities.add(projectile);
    }

    public void updateSelfMovableEntities(double secondsSinceLastFrame){
        for (Entity selfMovableEntity : selfMovableEntities) {
            entityController.moveForward(selfMovableEntity, secondsSinceLastFrame);
        }
    }

    public void updateUserMovableEntity(Entity entity, Movement movement, double secondsSinceLastFrame){
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
        }
    }

}
