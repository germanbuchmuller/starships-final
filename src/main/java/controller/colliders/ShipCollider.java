package controller.colliders;

import edu.austral.dissis.starships.vector.Vector2;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ShipCollider implements EntityCollider{
    private final Ship ship;

    public ShipCollider(@NotNull Ship ship) {
        this.ship=ship;
    }

    @Override
    public void handleCollisionWith(@NotNull Projectile projectile) {

    }

    @Override
    public void handleCollisionWith(@NotNull Asteroid asteroid) {

    }

    @Override
    public void handleCollisionWith(@NotNull Ship ship) {

    }

    @Override
    public Entity getEntity() {
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
        entityCollider.handleCollisionWith(ship);
    }


}
