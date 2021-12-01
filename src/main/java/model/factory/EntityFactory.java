package model.factory;

import edu.austral.dissis.starships.vector.Vector2;
import misc.BulletType;
import misc.Weapon;
import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface EntityFactory<T extends Entity> {
    T getEntity();
    void setImageFileName(String imageFileName);
    void setPosition(double x, double y, double angle);
    void setWidth(double width);
    void setHeight(double height);
    void setAcceleration(double acceleration);
    void setMaxSpeed(double maxSpeed);
    void setHealth(int health);
    void setMaxHealth(int maxHealth);
    void setMovementDirection(Vector2 direction);
}
