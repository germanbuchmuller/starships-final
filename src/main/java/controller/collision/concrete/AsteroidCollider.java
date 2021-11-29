package controller.collision.concrete;

import controller.collision.EntityCollider;
import controller.visitor.GameState;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.concrete.Asteroid;
import org.jetbrains.annotations.NotNull;

public class AsteroidCollider implements EntityCollider<Asteroid> {
    private final Asteroid asteroid;
    private final GameState gameState;

    public AsteroidCollider(@NotNull Asteroid asteroid,@NotNull GameState gameState) {
        this.asteroid=asteroid;
        this.gameState=gameState;
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
