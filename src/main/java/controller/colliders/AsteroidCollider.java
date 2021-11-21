package controller.colliders;

import model.Asteroid;
import model.Entity;
import model.EntityType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class AsteroidCollider implements EntityCollider{
    private Asteroid asteroid;

    public AsteroidCollider(@NotNull Asteroid asteroid) {
        this.asteroid=asteroid;
    }

    @Override
    public Entity getEntity() {
        return asteroid;
    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Rectangle(asteroid.getX(),asteroid.getY(),asteroid.getWidth(),asteroid.getHeight());
        shipShape.setRotate(asteroid.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        if (entityCollider.getEntity().getType()==EntityType.SHIP){
            harm(entityCollider.getEntity());
        }
    }

    private void harm(Entity entity) {
        entity.setHealth(Math.max((entity.getHealth() - asteroid.getDamage()), 0));
    }

}
