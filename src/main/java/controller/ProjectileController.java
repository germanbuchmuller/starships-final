package controller;

import model.concrete.Projectile;

public interface ProjectileController extends EntityController<Projectile> {
    void moveForward(Projectile projectile, double secondsSinceLastFrame);
}
