package controller.collision.concrete;

import controller.collision.EntityCollider;
import controller.visitor.GameState;
import edu.austral.dissis.starships.vector.Vector2;
import engine.GameCore;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.concrete.Asteroid;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AsteroidCollider implements EntityCollider<Asteroid> {
    private final Asteroid asteroid;
    private final GameState gameState;

    public AsteroidCollider(@NotNull Asteroid asteroid,GameState gameState) {
        this.asteroid=asteroid;
        this.gameState=gameState;

    }

    @Override
    public @NotNull Shape getShape() {
        return new Circle(asteroid.getX()+asteroid.getWidth()*0.5,asteroid.getY()+asteroid.getWidth()*0.5,asteroid.getWidth()*0.45);
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(this);
    }


    @Override
    public void handleCollisionWith(@NotNull ProjectileCollider projectileCollider) {
        projectileCollider.handleCollisionWith(this);
    }

    @Override
    public void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider) {
        Asteroid asteroid = asteroidCollider.getEntity();
        Vector2 ship1Vector = asteroid.getMovementDirection();
        Vector2 ship2Vector = this.asteroid.getMovementDirection();
        this.asteroid.setMovementDirection(this.asteroid.getMovementDirection().multiply(-0.5).add(ship1Vector.multiply(0.8)));
        asteroid.setMovementDirection(asteroid.getMovementDirection().multiply(-0.5).add(ship2Vector.multiply(0.8)));
        harm(asteroid);
    }

    @Override
    public void handleCollisionWith(@NotNull ShipCollider shipCollider) {
        Ship ship= shipCollider.getEntity();
        Vector2 asteroidVector = asteroid.getMovementDirection();
        Vector2 ship2Vector = ship.getMovementDirection();
        ship.setMovementDirection(ship.getMovementDirection().multiply(-0.5).add(asteroidVector.multiply(0.8)));
        asteroid.setMovementDirection(asteroid.getMovementDirection().multiply(-40/asteroid.getWidth()).add(ship2Vector.multiply(0.8)));
        harm(shipCollider.getEntity());
    }

    @Override
    public Asteroid getEntity() {
        return asteroid;
    }

    private void harm(Asteroid entity){
        entity.harm(asteroid.getDamage());
        asteroid.harm(entity.getDamage());
        if (entity.isDestroyed()){
            gameState.reject(entity);
        }
        if (asteroid.isDestroyed()){
            gameState.reject(asteroid);
        }
    }

    private void harm(Ship ship){
        ship.harm(asteroid.getDamage());
        if (!ship.isAlive()){
            //gameState.remove(ship);
        }
    }
}
