package controller.concrete;

import controller.ProjectileController;
import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Projectile;
import org.jetbrains.annotations.NotNull;

public class MyProjectileController implements ProjectileController {

    @Override
    public void move(Projectile entity, double secondsSinceLastFrame) {
        moveForward(entity, secondsSinceLastFrame);
    }

    @Override
    public void moveForward(Projectile projectile, double secondsSinceLastFrame) {
        accelerate(projectile,projectile.getAcceleration()*secondsSinceLastFrame);
    }

    private void accelerate(Projectile projectile, double amount){
        Vector2 newVector = projectile.getMovementDirection().add(Vector2.vectorFromModule(amount,Math.toRadians(projectile.getAngle())+Math.toRadians(270)));
        if (newVector.getModule()>projectile.getMaxSpeed()){
            newVector=newVector.asUnitary().multiply(projectile.getMaxSpeed());
        }
        projectile.setMovementDirection(newVector);
        updatePosition(projectile);
        projectile.setAccelerating(true);
    }

    private void updatePosition(Projectile projectile){
        double x =projectile.getX()+projectile.getMovementDirection().getX();
        double y =projectile.getY()+projectile.getMovementDirection().getY();
        projectile.setPosition(x, y, projectile.getAngle());
    }
}
