package controller.concrete;

import controller.AsteroidController;
import controller.ProjectileController;
import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Asteroid;
import model.concrete.Projectile;

public class MyAsteroidController implements AsteroidController {

    @Override
    public void move(Asteroid entity, double secondsSinceLastFrame) {
        moveForward(entity, secondsSinceLastFrame);
    }

    @Override
    public void moveForward(Asteroid asteroid, double secondsSinceLastFrame) {
        accelerate(asteroid,asteroid.getAcceleration()*secondsSinceLastFrame);
    }

    private void accelerate(Asteroid asteroid, double amount){
        Vector2 newVector = asteroid.getMovementDirection().add(Vector2.vectorFromModule(amount,Math.toRadians(asteroid.getAngle())+Math.toRadians(270)));
        if (newVector.getModule()>asteroid.getMaxSpeed()){
            newVector=newVector.asUnitary().multiply(asteroid.getMaxSpeed());
        }
        asteroid.setMovementDirection(newVector);
        updatePosition(asteroid);
        asteroid.setAccelerating(true);
    }

    private void updatePosition(Asteroid asteroid){
        double x =asteroid.getX()+asteroid.getMovementDirection().getX();
        double y =asteroid.getY()+asteroid.getMovementDirection().getY();
        asteroid.setPosition(x, y, asteroid.getAngle());
    }
}
