package controller.concrete;

import controller.ProjectileController;
import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

public class MyProjectileController extends MyAbstractEntityController implements ProjectileController {
    private double acceleration;

    public MyProjectileController(@NotNull Projectile projectile) {
        super(projectile);
        acceleration=5;
    }

    @Override
    public void move(double secondsSinceLastFrame) {
        moveForward(secondsSinceLastFrame);
    }

    @Override
    public void moveForward(double secondsSinceLastFrame) {
        moveWithDirection(acceleration*secondsSinceLastFrame,0);
    }

}
