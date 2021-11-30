package controller.collision.concrete;

import controller.collision.CollisionsEngine;
import controller.collision.EntityCollider;
import controller.visitor.GameState;
import misc.PlayersRepository;
import misc.PointsRepository;
import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyCollisionsEngine implements CollisionsEngine {
    private final Map<Entity, EntityCollider> collidersMap;
    private final EntityCollisionEngine collisionEngine;
    private final PlayersRepository playersRepository;
    private final PointsRepository pointsRepository;
    private final GameState gameState;

    public MyCollisionsEngine(@NotNull GameState gameState, @NotNull PlayersRepository playersRepository, @NotNull PointsRepository pointsRepository) {
        gameState.visit(this);
        collidersMap=new HashMap<>();
        collisionEngine=new EntityCollisionEngine();
        this.playersRepository=playersRepository;
        this.pointsRepository=pointsRepository;
        this.gameState=gameState;
    }

    @Override
    public void added(Ship ship) {
        collidersMap.put(ship,new ShipCollider(ship,gameState));
    }

    @Override
    public void added(Asteroid asteroid) {
        collidersMap.put(asteroid,new AsteroidCollider(asteroid,gameState));
    }

    @Override
    public void added(Projectile projectile) {
        collidersMap.put(projectile,new ProjectileCollider(projectile,playersRepository,pointsRepository, gameState));
    }

    @Override
    public void removed(Ship ship) {
        collidersMap.remove(ship);
    }

    @Override
    public void removed(Asteroid asteroid) {
        collidersMap.remove(asteroid);
    }

    @Override
    public void removed(Projectile projectile) {
        collidersMap.remove(projectile);
    }

    @Override
    public void update() {
        collisionEngine.checkCollisions(new ArrayList<>(getColliders()));
    }

    @Override
    public Collection<EntityCollider> getColliders() {
        return collidersMap.values();
    }
}
