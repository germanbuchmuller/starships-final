package misc;

import controller.visitor.EntityVisitor;
import engine.GameConfig;
import model.Projectile;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    private final List<EntityVisitor> visitors;
    private final int playerId;
    private BulletType bulletType;
    private long lastShotTime;

    public Weapon(int playerId) {
        this.playerId = playerId;
        visitors=new ArrayList<>();
        bulletType=BulletType.SMALL;
        lastShotTime=System.currentTimeMillis();
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public void accept(EntityVisitor visitor){
        if (!visitors.contains(visitor)) visitors.add(visitor);
    }

    public void shoot(double x, double y, double angle){
        int minTime=GameConfig.BULLET_MIN_TIME.get(bulletType);
        if (System.currentTimeMillis()-lastShotTime>=minTime){
            Projectile projectile= getProjectile(x,y,angle);
            for (EntityVisitor visitor : visitors) {
                projectile.accept(visitor);
            }
            lastShotTime=System.currentTimeMillis();
        }
    }

    private Projectile getProjectile(double x, double y, double angle){
        int damage = GameConfig.BULLET_DAMAGES.get(bulletType);
        int speed = GameConfig.BULLET_SPEED.get(bulletType);
        double width = GameConfig.BULLET_WIDTH.get(bulletType);
        double height = GameConfig.BULLET_HEIGHT.get(bulletType);
        String texture = GameConfig.BULLET_TEXTURE.get(bulletType);
        return new Projectile(damage,1, x, y, angle, width, height, texture, playerId);
    }

    public BulletType getBulletType() {
        return bulletType;
    }
}
