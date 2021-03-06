package model.concrete;

import misc.BulletType;
import misc.Weapon;
import model.PlayerRelated;
import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Ship extends AbstractEntity implements PlayerRelated {
    private int playerId;
    private Weapon weapon;
    private long lastRevive;
    private long lastBulletTypeChanged;

    public Ship(int maxHealth, double maxSpeed, double acceleration, Weapon weapon, double x, double y, double angle, double width, double height,String imageFileName, int playerId) {
       super(maxHealth, maxHealth, maxSpeed,acceleration,x,y,angle,width,height, imageFileName);
       this.playerId=playerId;
       this.weapon=weapon;
       lastBulletTypeChanged=System.currentTimeMillis();
    }

    public Ship(int maxHealth, int health, double maxSpeed, double acceleration, Weapon weapon, double x, double y, double angle, double width, double height,String imageFileName, int playerId) {
        super(maxHealth, health, maxSpeed,acceleration,x,y,angle,width,height, imageFileName);
        this.playerId=playerId;
        this.weapon=weapon;
        lastBulletTypeChanged=System.currentTimeMillis();
    }

    public void shoot(){
        if (isAlive()) weapon.shoot(getX(),getY(),getAngle());
    }

    public void setBulletType(@NotNull BulletType bulletType){
        weapon.setBulletType(bulletType);
    }

    public void changeBulletType(@NotNull BulletType bulletType){
        if (System.currentTimeMillis()-lastBulletTypeChanged>300){
            weapon.setBulletType(bulletType);
            lastBulletTypeChanged=System.currentTimeMillis();
            System.out.println(bulletType);
        }
    }


    public void setWeapon(@NotNull Weapon weapon){
        this.weapon=weapon;
    }

    public void revive(){
        super.revive();
        lastRevive=System.currentTimeMillis();
    }

    @Override
    public void harm(int amount) {
        if (System.currentTimeMillis()-lastRevive>3000){
            super.harm(amount);
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
