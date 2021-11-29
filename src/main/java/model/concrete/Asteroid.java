package model.concrete;

import model.AbstractEntity;
import model.EntityType;
import model.SelfMovable;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;
import serialize.SerializedAsteroid;

public class Asteroid extends AbstractEntity implements SelfMovable {
    private final int damage;
    public Asteroid(int damage, int health, double maxSpeed, double x, double y, double angle, double width, double height,String imageFileName) {
        super(health, maxSpeed,x,y,angle,width,height,imageFileName);
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public void harm(int amount) {
        super.harm(amount);
        if (getHealth()==0) destroy();
    }
}