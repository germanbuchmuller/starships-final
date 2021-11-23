package model;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;
import serialize.SerializedAsteroid;

public class Asteroid extends AbstractEntity {
    private final int damage;
    public Asteroid(int damage, int health, int maxSpeed, int rewardPoints, double x, double y, double angle, double width, double height,String imageFileName) {
        super(health,maxSpeed,rewardPoints,x,y,angle,width,height,imageFileName);
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public EntityType getType() {
        return EntityType.ASTEROID;
    }

    @Override
    public int getPlayerID() {
        return -1;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public SerializedAsteroid toSerializedEntity() {
            return new SerializedAsteroid(getHealth(), getMaxSpeed(), getRewardPoints(), getDamage(), getX(), getY(), getAngle(), getWidth(), getHeight(), getImageFileName());
        }
}