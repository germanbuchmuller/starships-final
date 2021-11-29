package controller.colliders;

import edu.austral.dissis.starships.vector.Vector2;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ShipCollider implements EntityCollider<Ship>{
    private final Ship ship;

    public ShipCollider(@NotNull Ship ship) {
        this.ship=ship;
    }

    @Override
    public void handleCollisionWith(@NotNull ProjectileCollider projectileCollider) {
        projectileCollider.handleCollisionWith(this);
    }

    @Override
    public void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider) {
        asteroidCollider.handleCollisionWith(this);
    }

    @Override
    public void handleCollisionWith(@NotNull ShipCollider shipCollider) {
        Ship ship = shipCollider.getEntity();
        Vector2 ship1Vector = ship.getMovementDirection();
        Vector2 ship2Vector = this.ship.getMovementDirection();
        this.ship.setMovementDirection(this.ship.getMovementDirection().multiply(-0.5).add(ship1Vector.multiply(0.8)));
        ship.setMovementDirection(ship.getMovementDirection().multiply(-0.5).add(ship2Vector.multiply(0.8)));
    }

    @Override
    public Ship getEntity() {
        return ship;
    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Rectangle(ship.getX(),ship.getY(),ship.getWidth(),ship.getHeight());
        shipShape.setRotate(ship.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(this);
    }


}
