package model;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public interface Entity {
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
    String getImageFileName();
    void setPosition(double x,double y,double angle);
    void accept(@NotNull EntityVisitor visitor);
}
