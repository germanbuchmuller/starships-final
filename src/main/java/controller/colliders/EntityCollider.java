package controller.colliders;

import edu.austral.dissis.starships.collision.Collider;
import model.Asteroid;
import model.Entity;
import model.Projectile;
import model.Ship;
import org.jetbrains.annotations.NotNull;

public interface EntityCollider extends Collider<EntityCollider> {
    void handleCollisionWith(@NotNull Projectile projectile);
    void handleCollisionWith(@NotNull Asteroid asteroid);
    void handleCollisionWith(@NotNull Ship ship);
    Entity getEntity();
}
