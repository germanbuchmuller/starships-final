package model.factory;

import misc.BulletType;
import misc.Weapon;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityFactory {
    Ship getShip(Weapon weapon, double x, double y, String imageFileName, int playerId);
    Ship getShip(Weapon weapon,String imageFileName, int playerId);
    void reviveShip(Ship ship);
    Asteroid getAsteroid();
    Projectile getProjectile(double x, double y, double angle, BulletType bulletType, int playerId);
}
