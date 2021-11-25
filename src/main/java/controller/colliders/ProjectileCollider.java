package controller.colliders;

import engine.PlayersRepository;
import model.Entity;
import model.EntityType;
import model.Projectile;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ProjectileCollider implements EntityCollider{
    private final Projectile projectile;
    private final PlayersRepository playersRepository;

    public ProjectileCollider(@NotNull Projectile projectile, @NotNull PlayersRepository playersRepository) {
        this.projectile=projectile;
        this.playersRepository = playersRepository;
    }

    @Override
    public Entity getEntity() {
        return projectile;
    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Rectangle(projectile.getX(),projectile.getY(),projectile.getWidth(),projectile.getHeight());
        shipShape.setRotate(projectile.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        if (entityCollider.getEntity().getPlayerID()!=projectile.getPlayerID() && entityCollider.getEntity().getType()!=EntityType.PROJECTILE){
            harm(entityCollider.getEntity());
            projectile.destroy();
            playersRepository.addPointsToPlayer(projectile.getPlayerID(),projectile.getRewardPoints());
        }else if (entityCollider.getEntity().getType()==EntityType.PROJECTILE && entityCollider.getEntity().getPlayerID()!=projectile.getPlayerID() ){
            projectile.destroy();
        }
    }

    private void harm(Entity entity) {
        entity.setHealth(Math.max((entity.getHealth() - projectile.getDamage()), 0));
        if (entity.getHealth()==0) {
            playersRepository.addPointsToPlayer(projectile.getPlayerID(), entity.getRewardPoints());
            entity.destroy();
        }
    }

}
