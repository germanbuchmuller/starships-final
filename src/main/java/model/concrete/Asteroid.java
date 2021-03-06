package model.concrete;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Asteroid extends AbstractEntity {
    private final int damage;
    public Asteroid(int damage, int health, double maxSpeed, double acceleration, double x, double y, double angle, double width, double height,String imageFileName) {
        super(health, health, maxSpeed, acceleration,x,y,angle,width,height,imageFileName);
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }

}