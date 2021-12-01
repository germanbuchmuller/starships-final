package serialize.concrete;

import model.Entity;
import serialize.SerializedEntity;

public abstract class AbstractSerializedEntity implements SerializedEntity {
    final int health, maxHealth;
    final double maxSpeed, acceleration, x, y, angle, vectorX, vectorY, width, height;
    final String imageFileName;

    public AbstractSerializedEntity(Entity entity) {
        this.health=entity.getHealth();
        this.maxHealth=entity.getMaxHealth();
        this.maxSpeed=entity.getMaxSpeed();
        this.acceleration=entity.getAcceleration();
        this.x=entity.getX();
        this.y=entity.getY();
        this.angle=entity.getAngle();
        this.vectorX=entity.getMovementDirection().getX();
        this.vectorY=entity.getMovementDirection().getY();
        this.width=entity.getWidth();
        this.height=entity.getHeight();
        this.imageFileName=entity.getImageFileName();
    }
}
