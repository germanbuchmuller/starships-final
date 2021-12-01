package controller.collision.concrete;

import controller.collision.EntityCollider;
import controller.visitor.GameState;
import misc.PlayersRepository;
import misc.PointsRepository;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

public class ProjectileCollider implements EntityCollider<Projectile> {
    private final Projectile projectile;
    private final PlayersRepository playersRepository;
    private final PointsRepository pointsRepository;
    private final GameState gameState;

    public ProjectileCollider(@NotNull Projectile projectile, @NotNull PlayersRepository playersRepository, @NotNull PointsRepository pointsRepository, @NotNull GameState gameState) {
        this.projectile=projectile;
        this.playersRepository = playersRepository;
        this.pointsRepository= pointsRepository;
        this.gameState=gameState;
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
            gameState.reject(projectile);
            gameState.reject(this.projectile);
        }
    }

    @Override
    public void handleCollisionWith(@NotNull AsteroidCollider asteroidCollider) {
        Asteroid asteroid=asteroidCollider.getEntity();
        harm(asteroid);
        gameState.reject(this.projectile);
    }

    @Override
    public void handleCollisionWith(@NotNull ShipCollider shipCollider) {
        Ship ship = shipCollider.getEntity();
        if (ship.getPlayerId()!=projectile.getPlayerId()){
            harm(ship);
            gameState.reject(this.projectile);
        }
    }

    private void harm(Ship ship){
        harmEntity(ship);
        if (!ship.isAlive()){
            playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(ship));
            //gameState.remove(ship);
        }
    }

    private void harm(Asteroid asteroid){
        harmEntity(asteroid);
        if (!asteroid.isAlive()){
            playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(asteroid));
            gameState.reject(asteroid);
        }
    }

    private void harmEntity(Entity entity){
        entity.harm(projectile.getDamage());
        playersRepository.addPointsToPlayer(projectile.getPlayerId(),pointsRepository.getPoints(projectile));
    }

    @Override
    public Projectile getEntity() {
        return projectile;
    }
}
