package controller.colliders;

import engine.PlayersRepository;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ProjectileCollider implements EntityCollider {
    private final Projectile projectile;
    private final PlayersRepository playersRepository;

    public ProjectileCollider(@NotNull Projectile projectile, @NotNull PlayersRepository playersRepository) {
        this.projectile=projectile;
        this.playersRepository = playersRepository;
    }

    @Override
    public Projectile getEntity() {
        return projectile;
    }

    @Override
    public void handleCollisionWith(@NotNull Projectile projectile) {

    }

    @Override
    public void handleCollisionWith(@NotNull Asteroid asteroid) {

    }

    @Override
    public void handleCollisionWith(@NotNull Ship ship) {

    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Rectangle(projectile.getX(),projectile.getY(),projectile.getWidth(),projectile.getHeight());
        shipShape.setRotate(projectile.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(projectile);
    }


}
