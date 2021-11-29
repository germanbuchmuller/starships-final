package controller;

import controller.EntityController;

public interface ShipController extends EntityController {
    void setShipAcceleration(double acceleration);
    void moveForward(double secondsSinceLastFrame);
    void moveBackwards(double secondsSinceLastFrame);
    void rotateRight(double secondsSinceLastFrame);
    void rotateLeft(double secondsSinceLastFrame);
    void slowDown(double secondsSinceLastFrame);
    void shoot(double secondsSinceLastFrame);
}
