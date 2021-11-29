package controller.collision;

import controller.collision.concrete.AsteroidCollider;
import controller.collision.concrete.ProjectileCollider;
import controller.collision.concrete.ShipCollider;
import edu.austral.dissis.starships.collision.Collider;
import model.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityCollider<T extends Entity> extends Collider<EntityCollider<T>> {
    void handleCollisionWith(@NotNull ProjectileCollider projectileCollider);
    void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider);
    void handleCollisionWith(@NotNull ShipCollider shipCollider);
    T getEntity();
}
