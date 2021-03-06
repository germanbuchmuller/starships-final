package controller.collision.concrete;

import controller.collision.EntityCollider;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityCollisionEngine {
    public EntityCollisionEngine() {
    }

    @NotNull
    private static EntityCollider head(@NotNull List<EntityCollider> list) {
        return list.get(0);
    }

    @NotNull
    private static List<EntityCollider> tail(List<EntityCollider> list) {
        return list.subList(1, list.size());
    }

    @NotNull
    public void checkCollisions(@NotNull List<EntityCollider> colliders) {
        if (!colliders.isEmpty()) {
            this.checkCollisions(head(colliders), tail(colliders));
        }
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

    @NotNull
    private void checkCollisions(@NotNull EntityCollider current, @NotNull List<EntityCollider> colliders) {
        if (!colliders.isEmpty()) {
            colliders.forEach((collider) -> {
                if (this.testIntersection(current.getShape(), collider.getShape())) {
                    current.handleCollisionWith(collider);
                }

            });
            this.checkCollisions(head(colliders), tail(colliders));
        }
    }
}
