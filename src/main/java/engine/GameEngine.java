package engine;

import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface GameEngine {
    void add(Ship ship);
    void add(Asteroid asteroid);
    void add(Projectile projectile);
    void remove(Ship ship);
    void remove(Asteroid asteroid);
    void remove(Projectile projectile);
}
