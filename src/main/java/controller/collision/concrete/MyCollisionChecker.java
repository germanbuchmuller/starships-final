package controller.collision.concrete;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class MyCollisionChecker implements CollisionChecker {
    private Collection<EntityCollider> colliders;

    public MyCollisionChecker(Collection<EntityCollider> colliders) {
        this.colliders = colliders;
    }

    @Override
    public boolean isColliding(EntityCollider collider) {
        boolean collides = false;
        for (EntityCollider entityCollider : colliders) {
            if (collider.getEntity()!=entityCollider.getEntity()&&testIntersection(collider.getShape(),entityCollider.getShape())){
                collides=true;
                break;
            }
        }
        return collides;
    }

    @Override
    public void setColliders(Collection<EntityCollider> colliders) {
        this.colliders=colliders;
    }

    @NotNull
    private boolean testIntersection(@NotNull Shape shapeA, @NotNull Shape shapeB) {
        boolean layoutIntersects = shapeA.getBoundsInParent().intersects(shapeB.getBoundsInParent());
        if (!layoutIntersects) {
            return false;
        } else {
            Shape shapeIntersection = Shape.intersect(shapeA, shapeB);
            return !shapeIntersection.getLayoutBounds().isEmpty();
        }
    }
}
