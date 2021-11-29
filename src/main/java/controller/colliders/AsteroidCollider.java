package controller.colliders;

import edu.austral.dissis.starships.collision.Collider;
import javafx.scene.shape.Circle;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class AsteroidCollider implements EntityCollider<Asteroid> {
    private final Asteroid asteroid;

    public AsteroidCollider(@NotNull Asteroid asteroid) {
        this.asteroid=asteroid;
    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Circle(asteroid.getX()+asteroid.getWidth()/2,asteroid.getY()+asteroid.getWidth()/2,asteroid.getWidth()/2);
        shipShape.setRotate(asteroid.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(this);
    }


    @Override
    public void handleCollisionWith(@NotNull ProjectileCollider projectileCollider) {

    }

    @Override
    public void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider) {

    }

    @Override
    public void handleCollisionWith(@NotNull ShipCollider shipCollider) {

    }

    @Override
    public Asteroid getEntity() {
        return asteroid;
    }
}
