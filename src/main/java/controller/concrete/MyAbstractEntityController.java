package controller.concrete;

import controller.EntityController;
import edu.austral.dissis.starships.vector.Vector2;
import model.Entity;

public abstract class MyAbstractEntityController implements EntityController {
    private Entity entity;

    public MyAbstractEntityController(Entity entity) {
        this.entity = entity;
    }

    void moveWithDirection(double amount, double direction){
        Vector2 newVector = entity.getMovementDirection().add(Vector2.vectorFromModule(amount,Math.toRadians(entity.getAngle())+Math.toRadians(direction+270)));
        if (newVector.getModule()>entity.getMaxSpeed()){
            newVector=newVector.asUnitary().multiply(entity.getMaxSpeed());
        }
        entity.setMovementDirection(newVector);
        updatePosition();
        entity.setAccelerating(true);
    }

    void updatePosition(){
        double x =entity.getX()+entity.getMovementDirection().getX();
        double y =entity.getY()+entity.getMovementDirection().getY();
        entity.setPosition(x, y, entity.getAngle());
    }
}
