package controller;

import model.concrete.Ship;

public interface ShipController extends EntityController<Ship> {
    void moveForward(Ship ship, double secondsSinceLastFrame);
    void moveBackwards(Ship ship,double secondsSinceLastFrame);
    void rotateRight(Ship ship,double secondsSinceLastFrame);
    void rotateLeft(Ship ship,double secondsSinceLastFrame);
    void slowDown(Ship ship,double secondsSinceLastFrame);
    void shoot(Ship ship);
    void changeBulletType(Ship ship);
}
