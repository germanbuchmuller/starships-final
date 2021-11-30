package controller.concrete;

import controller.EntityController;
import controller.ShipController;
import edu.austral.dissis.starships.vector.Vector2;
import model.Entity;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

public class MyShipController  implements ShipController {

    @Override
    public void move(Ship ship, double secondsSinceLastFrame) {
        slowDown(ship, secondsSinceLastFrame);
    }

    @Override
    public void moveForward(Ship ship,double secondsSinceLastFrame) {
        moveWithDirection(ship,ship.getAcceleration()*secondsSinceLastFrame,0);
    }

    @Override
    public void moveBackwards(Ship ship,double secondsSinceLastFrame) {
        moveWithDirection(ship,ship.getAcceleration()*secondsSinceLastFrame,180);
    }

    @Override
    public void rotateLeft(Ship ship,double secondsSinceLastFrame) {
        ship.setPosition(ship.getX(), ship.getY(), ship.getAngle()-ship.getAcceleration()*80*secondsSinceLastFrame);
    }

    @Override
    public void rotateRight(Ship ship,double secondsSinceLastFrame) {
        ship.setPosition(ship.getX(), ship.getY(), ship.getAngle()+ship.getAcceleration()*80*secondsSinceLastFrame);
    }

    @Override
    public void slowDown(Ship ship,double secondsSinceLastFrame) {
        if (!ship.isAccelerating()){
            Vector2 oppositeVector = Vector2.vectorFromModule(ship.getMovementDirection().getModule()*0.015,ship.getMovementDirection().getAngle()+Math.toRadians(180));
            ship.setMovementDirection(ship.getMovementDirection().add(oppositeVector));
            updatePosition(ship);
        }
        ship.setAccelerating(false);
    }

    @Override
    public void shoot(Ship ship) {
        ship.shoot();
    }

    private void moveWithDirection(Ship ship,double amount, double direction){
        Vector2 newVector = ship.getMovementDirection().add(Vector2.vectorFromModule(amount,Math.toRadians(ship.getAngle())+Math.toRadians(direction+270)));
        if (newVector.getModule()>ship.getMaxSpeed()){
            newVector=newVector.asUnitary().multiply(ship.getMaxSpeed());
        }
        ship.setMovementDirection(newVector);
        updatePosition(ship);
        ship.setAccelerating(true);
    }

    private void updatePosition(Ship ship){
        double x =ship.getX()+ship.getMovementDirection().getX();
        double y =ship.getY()+ship.getMovementDirection().getY();
        ship.setPosition(x, y, ship.getAngle());
    }

}
