package model;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Projectile extends AbstractEntity{
    private final int damage;
    private final int playerID;

    public Projectile(int damage, int health, int maxSpeed, int rewardPoints, double x, double y, double angle, double width, double height, int playerID,String imageFileName) {
        super(health,maxSpeed,rewardPoints,x,y,angle,width,height,imageFileName);
        this.damage=damage;
        this.playerID=playerID;
    }

    public int getDamage() {
        return damage;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public EntityType getType() {
        return EntityType.PROJECTILE;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }
}
