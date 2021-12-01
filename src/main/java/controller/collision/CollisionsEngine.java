package controller.collision;

import core.GameEngine;

import java.util.Collection;

public interface CollisionsEngine extends GameEngine {
    void update();
    Collection<EntityCollider> getColliders();
}
