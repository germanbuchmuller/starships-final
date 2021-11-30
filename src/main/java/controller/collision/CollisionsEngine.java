package controller.collision;

import engine.GameEngine;

import java.util.Collection;
import java.util.List;

public interface CollisionsEngine extends GameEngine {
    void update();
    Collection<EntityCollider> getColliders();
}
