package model.factory.concrete;

import edu.austral.dissis.starships.vector.Vector2;
import model.Entity;
import model.factory.EntityFactory;

public abstract class AbstractEntityFactory<T extends Entity> implements EntityFactory<T> {
    int health, maxHealth;
    double x, y, angle, width, height, acceleration, maxSpeed;
    String imageFileName;
    Vector2 movementDirection;
    @Override
    public void setImageFileName(String imageFileName) {
        this.imageFileName=imageFileName;
    }

    @Override
    public void setPosition(double x, double y, double angle) {
        this.x=x;
        this.y=y;
        this.angle=angle;
    }

    @Override
    public void setWidth(double width) {
        this.width=width;
    }

    @Override
    public void setHeight(double height) {
        this.height=height;

    }

    @Override
    public void setAcceleration(double acceleration) {
        this.acceleration=acceleration;

    }

    @Override
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed=maxSpeed;

    }

    @Override
    public void setHealth(int health) {
        this.health=health;

    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth=maxHealth;

    }

    @Override
    public void setMovementDirection(Vector2 direction) {
        this.movementDirection=direction;
    }
}
