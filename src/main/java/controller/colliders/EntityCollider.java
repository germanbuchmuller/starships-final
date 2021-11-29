package controller.colliders;

import edu.austral.dissis.starships.collision.Collider;
import model.Asteroid;
import model.Entity;
import model.Projectile;
import model.Ship;
import org.jetbrains.annotations.NotNull;

public interface EntityCollider<T extends Entity> extends Collider<EntityCollider<T>> {
    void handleCollisionWith(@NotNull ProjectileCollider projectileCollider);
    void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider);
    void handleCollisionWith(@NotNull ShipCollider shipCollider);
    T getEntity();
}
