package model;

import misc.BulletType;
import misc.Weapon;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Ship extends AbstractEntity{
    private final int playerID;
    private Weapon weapon;

    public Ship(int health, int maxSpeed, int rewardPoints, double x, double y, double angle, double width, double height,String imageFileName, int playerID) {
       super(health,maxSpeed,rewardPoints,x,y,angle,width,height, imageFileName);
       this.playerID=playerID;
       weapon=new Weapon(playerID);
    }

    public void shoot(){
        if (!isDestroyed()) weapon.shoot(getX(),getY(),getAngle());
    }

    public void setBulletType(@NotNull BulletType bulletType){
        weapon.setBulletType(bulletType);
    }

    @Override
    public EntityType getType() {
        return EntityType.SHIP;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
        weapon.accept(visitor);
    }
}
