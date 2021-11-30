package controller;

import model.concrete.Asteroid;
import model.concrete.Projectile;

public interface AsteroidController extends EntityController<Asteroid> {
    void moveForward(Asteroid asteroid, double secondsSinceLastFrame);
}
