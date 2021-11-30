package controller.visitor;

import controller.collision.*;
import controller.collision.concrete.EntityCollisionEngine;
import misc.PlayersRepository;
import misc.PointsRepository;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class CollisionsVisitor implements EntityVisitor, Serializable {
    private final List<EntityCollider> colliders;
    private final List<EntityCollider> collidersToRemove;
    private final EntityCollisionEngine collisionEngine;
    private final PlayersRepository playersRepository;
    private final PointsRepository pointsRepository;

    public CollisionsVisitor(@NotNull PlayersRepository playersRepository, @NotNull PointsRepository pointsRepository) {
        colliders=new ArrayList<>();
        collidersToRemove=new ArrayList<>();
        collisionEngine=new EntityCollisionEngine();
        this.playersRepository = playersRepository;
        this.pointsRepository = pointsRepository;
    }

    @Override
    public void visit(Ship ship){
        //colliders.add(new ShipCollider(ship));
    }

    @Override
    public void visit(Asteroid asteroid) {
        //colliders.add(new AsteroidCollider(asteroid));
    }

    @Override
    public void visit(Projectile projectile) {
        //colliders.add(new ProjectileCollider(projectile, playersRepository,pointsRepository));
    }

    public void checkColisions(){
        collisionEngine.checkCollisions(colliders);
        for (EntityCollider collider : colliders) {
            if (collider.getEntity().isDestroyed()){
                collidersToRemove.add(collider);
            }
        }
        colliders.removeAll(collidersToRemove);
        collidersToRemove.clear();
    }

}
