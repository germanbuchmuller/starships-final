package controller.concrete;

import controller.*;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public class MyEntityControllerProvider implements EntityControllerProvider {
    @Override
    public ShipController get(Ship ship) {
        return new MyShipController();
    }

    @Override
    public ProjectileController get(Projectile projectile) {
        return new MyProjectileController();
    }

    @Override
    public AsteroidController get(Asteroid asteroid) {
        return new MyAsteroidController();
    }
}
