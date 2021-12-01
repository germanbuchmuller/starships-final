package core;

import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface GameEngine {
    void added(Ship ship);
    void added(Asteroid asteroid);
    void added(Projectile projectile);
    void removed(Ship ship);
    void removed(Asteroid asteroid);
    void removed(Projectile projectile);
}
