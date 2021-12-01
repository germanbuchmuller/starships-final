package controller.collision;

import java.util.Collection;
import java.util.List;

public interface CollisionChecker {
    boolean isColliding(EntityCollider collider);
    void setColliders(Collection<EntityCollider> colliders);
}
