package model;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;
import serialize.SerializableEntity;

public interface Entity extends SerializableEntity {
    EntityType getType();
    double getX();
    double getY();
    double getAngle();
    double getWidth();
    double getHeight();
    int getMaxSpeed();
    int getHealth();
    void setHealth(int health);
    int getRewardPoints();
    int getPlayerID();
    void destroy();
    boolean isDestroyed();
    String getImageFileName();
    void setPosition(double x,double y,double angle);
    void accept(@NotNull EntityVisitor visitor);
}
