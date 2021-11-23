package controller.visitor;

import controller.colliders.AsteroidCollider;
import controller.colliders.EntityCollider;
import controller.colliders.ProjectileCollider;
import controller.colliders.ShipCollider;
import engine.PlayersManager;
import edu.austral.dissis.starships.collision.CollisionEngine;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class CollisionsVisitor implements EntityVisitor, Serializable {
    private final List<EntityCollider> colliders;
    private final List<EntityCollider> collidersToRemove;
    private final CollisionEngine collisionEngine;
    private final PlayersManager playersManager;

    public CollisionsVisitor(@NotNull PlayersManager playersManager) {
        colliders=new ArrayList<>();
        collidersToRemove=new ArrayList<>();
        collisionEngine=new CollisionEngine();
        this.playersManager = playersManager;
    }

    @Override
    public void visit(Ship ship){
        colliders.add(new ShipCollider(ship));
    }

    @Override
    public void visit(Asteroid asteroid) {
        colliders.add(new AsteroidCollider(asteroid));
    }

    @Override
    public void visit(Projectile projectile) {
        colliders.add(new ProjectileCollider(projectile, playersManager));
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
