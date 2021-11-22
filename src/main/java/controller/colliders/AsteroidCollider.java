package controller.colliders;

import javafx.scene.shape.Circle;
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
        Shape shipShape = new Circle(asteroid.getX()+asteroid.getWidth()/2,asteroid.getY()+asteroid.getWidth()/2,asteroid.getWidth()/2);
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
        if (entity.getHealth()==0){
            entity.destroy();
        }
    }

}
