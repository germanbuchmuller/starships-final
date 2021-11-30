package controller.collision;

import java.util.List;

public interface CollisionChecker {
    boolean isColliding(EntityCollider collider, List<EntityCollider> colliders);
}
