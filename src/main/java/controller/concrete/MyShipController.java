package controller.concrete;

import controller.ShipController;
import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

public class MyShipController extends MyAbstractEntityController implements ShipController {
    private final Ship ship;
    private double acceleration;

    public MyShipController(@NotNull Ship ship) {
        super(ship);
        this.ship = ship;
        acceleration=3;
    }

    @Override
    public void move(double secondsSinceLastFrame) {
        slowDown(secondsSinceLastFrame);
    }

    @Override
    public void moveForward(double secondsSinceLastFrame) {
        moveWithDirection(acceleration*secondsSinceLastFrame,0);
    }

    @Override
    public void moveBackwards(double secondsSinceLastFrame) {
        moveWithDirection(acceleration*secondsSinceLastFrame,180);
    }

    @Override
    public void rotateLeft(double secondsSinceLastFrame) {
        ship.setPosition(ship.getX(), ship.getY(), ship.getAngle()-acceleration*80*secondsSinceLastFrame);
    }

    @Override
    public void rotateRight(double secondsSinceLastFrame) {
        ship.setPosition(ship.getX(), ship.getY(), ship.getAngle()+acceleration*80*secondsSinceLastFrame);
    }

    @Override
    public void slowDown(double secondsSinceLastFrame) {
        if (!ship.isAccelerating()){
            Vector2 oppositeVector = Vector2.vectorFromModule(ship.getMovementDirection().getModule()*0.015,ship.getMovementDirection().getAngle()+Math.toRadians(180));
            ship.setMovementDirection(ship.getMovementDirection().add(oppositeVector));
            updatePosition();
        }
        ship.setAccelerating(false);
    }

    @Override
    public void shoot(double secondsSinceLastFrame) {
        ship.shoot();
    }


    @Override
    public void setShipAcceleration(double acceleration) {
        this.acceleration=acceleration;
    }

}
