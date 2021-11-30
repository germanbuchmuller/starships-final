package controller.collision.concrete;

import controller.collision.EntityCollider;
import controller.visitor.GameState;
import edu.austral.dissis.starships.vector.Vector2;
import engine.GameCore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.concrete.Asteroid;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

public class ShipCollider implements EntityCollider<Ship> {
    private final Ship ship;
    private final GameState gameState;

    public ShipCollider(@NotNull Ship ship,GameState gameState) {
        this.ship=ship;
        this.gameState=gameState;
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
        Shape shipShape = new Circle(ship.getX()+ship.getWidth()*0.5,ship.getY()+ship.getWidth()*0.5,ship.getWidth()*0.5);
        shipShape.setRotate(ship.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(this);
    }

}
