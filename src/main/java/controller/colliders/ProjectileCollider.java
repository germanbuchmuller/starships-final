package controller.colliders;

import misc.MyPlayersRepository;
import misc.PlayersRepository;
import misc.PointsRepository;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class ProjectileCollider implements EntityCollider<Projectile> {
    private final Projectile projectile;
    private final PlayersRepository playersRepository;
    private final PointsRepository pointsRepository;

    public ProjectileCollider(@NotNull Projectile projectile, @NotNull PlayersRepository playersRepository, @NotNull PointsRepository pointsRepository) {
        this.projectile=projectile;
        this.playersRepository = playersRepository;
        this.pointsRepository= pointsRepository;
    }

    @Override
    public @NotNull Shape getShape() {
        Shape shipShape = new Rectangle(projectile.getX(),projectile.getY(),projectile.getWidth(),projectile.getHeight());
        shipShape.setRotate(projectile.getAngle());
        return shipShape;
    }

    @Override
    public void handleCollisionWith(@NotNull EntityCollider entityCollider) {
        entityCollider.handleCollisionWith(this);
    }


    @Override
    public void handleCollisionWith(@NotNull ProjectileCollider projectileCollider) {
        Projectile projectile = projectileCollider.getEntity();
        if (projectile.getPlayerId()!=this.projectile.getPlayerId()){
            projectile.destroy();
            this.projectile.destroy();
        }
    }

    @Override
    public void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider) {
        Asteroid asteroid=asteroidCollider.getEntity();
        harm(asteroid);
    }

    @Override
    public void handleCollisionWith(@NotNull ShipCollider shipCollider) {
        Ship ship = shipCollider.getEntity();
        if (ship.getPlayerId()!=projectile.getPlayerId()){
            harm(ship);
        }
    }

    private void harm(Ship ship){
        harmEntity(ship);
        if (ship.isDestroyed()){
            playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(ship));
        }
    }

    private void harm(Asteroid asteroid){
        harmEntity(asteroid);
        if (asteroid.isDestroyed()){
            playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(asteroid));
        }
    }

    private void harmEntity(Entity entity){
        entity.harm(projectile.getDamage());
        projectile.destroy();
        playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(projectile));
    }

    @Override
    public Projectile getEntity() {
        return projectile;
    }
}
