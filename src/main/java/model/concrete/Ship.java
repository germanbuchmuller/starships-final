package model.concrete;

import misc.BulletType;
import misc.Weapon;
import model.AbstractEntity;
import model.EntityType;
import model.PlayerRelated;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Ship extends AbstractEntity implements PlayerRelated {
    private int playerId;
    private final Weapon weapon;
    private long lastRevive;

    public Ship(int health, double maxSpeed, double acceleration, double x, double y, double angle, double width, double height,String imageFileName, int playerId) {
       super(health, maxSpeed,acceleration,x,y,angle,width,height, imageFileName);
       this.playerId=playerId;
       weapon=new Weapon(playerId);
    }

    public void shoot(){
        if (!isDestroyed()) weapon.shoot(getX(),getY(),getAngle());
    }

    public void setBulletType(@NotNull BulletType bulletType){
        weapon.setBulletType(bulletType);
    }

    public void revive(double x, double y){
        super.revive();
        lastRevive=System.currentTimeMillis();
        setPosition(x,y,0);
    }

    @Override
    public void destroy() {
        if (System.currentTimeMillis()-lastRevive>3000){
            super.destroy();
        }
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
        weapon.accept(visitor);
    }

    public BulletType getBulletType(){
        return weapon.getBulletType();
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
