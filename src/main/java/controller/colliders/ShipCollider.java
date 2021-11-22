package controller.colliders;

import edu.austral.dissis.starships.vector.Vector2;
import model.Entity;
import model.EntityType;
import model.Ship;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ShipCollider implements EntityCollider{
    private final Ship ship;

    public ShipCollider(@NotNull Ship ship) {
        this.ship=ship;
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
        if (entityCollider.getEntity().getPlayerID()!=ship.getPlayerID() && entityCollider.getEntity().getType() != EntityType.PROJECTILE){
            Vector2 vector = Vector2.vectorFromModule(ship.getHeight(),Math.toRadians(ship.getAngle())+Math.toRadians(90));
            ship.setPosition(ship.getX()+vector.getX(), ship.getY()+vector.getY(), ship.getAngle());
        }
    }


}
