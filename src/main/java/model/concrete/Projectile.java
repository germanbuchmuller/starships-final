package model.concrete;

import model.PlayerRelated;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Projectile extends AbstractEntity implements PlayerRelated {
    private final int damage;
    private int playerId;

    public Projectile(int damage, int health, double maxSpeed, double acceleration, double x, double y, double angle, double width, double height, String imageFileName, int playerId) {
        super(health, health, maxSpeed,acceleration,x,y,angle,width,height,imageFileName);
        this.damage=damage;
        this.playerId=playerId;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId=playerId;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }
}
