package controller.colliders;

import edu.austral.dissis.starships.collision.Collider;
import model.Entity;

public interface EntityCollider extends Collider<EntityCollider> {
    Entity getEntity();
}
