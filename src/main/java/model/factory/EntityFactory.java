package model.factory;

import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityFactory {
    Ship getShip(double x, double y, String imageFileName, int playerId);
    Ship getShip(String imageFileName, int playerId);
    Asteroid getAsteroid();
    Projectile getProjectile();
}
