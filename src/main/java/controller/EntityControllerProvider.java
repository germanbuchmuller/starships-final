package controller;

import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityControllerProvider {
    ShipController get(Ship ship);
    ProjectileController get(Projectile projectile);
    AsteroidController get(Asteroid asteroid);
}
