package model;

import edu.austral.dissis.starships.vector.Vector2;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;
import serialize.SerializableEntity;

public interface Entity{
    double getX();
    double getY();
    double getAngle();
    double getWidth();
    double getHeight();
    Vector2 getMovementDirection();
    void setMovementDirection(Vector2 direction);
    double getMaxSpeed();
    double getAcceleration();
    int getHealth();
    int getMaxHealth();
    void harm(int amount);
    void heal(int amount);
    boolean isAlive();
    void revive();
    String getImageFileName();
    void setAccelerating(boolean accelerating);
    boolean isAccelerating();
    void setPosition(double x,double y,double angle);
    void accept(@NotNull EntityVisitor visitor);
}
