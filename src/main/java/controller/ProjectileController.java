package controller;

public interface ProjectileController extends EntityController {
    void moveForward(double secondsSinceLastFrame);
}
