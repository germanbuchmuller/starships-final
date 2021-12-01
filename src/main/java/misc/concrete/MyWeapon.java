package misc.concrete;

import controller.visitor.EntityVisitor;
import engine.GameConfig;
import engine.concrete.MyGameConfig;
import misc.BulletType;
import misc.Weapon;
import model.concrete.Projectile;
import model.factory.EntityFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWeapon implements Weapon {
    private final Map<BulletType, Integer> coolDowns;
    private final List<EntityVisitor> visitors;
    private final EntityFactory entityFactory;
    private final int playerId;
    private BulletType bulletType;
    private long lastShotTime;


    public MyWeapon(@NotNull Map<BulletType, Integer> coolDowns, @NotNull EntityFactory entityFactory, int playerId) {
        visitors=new ArrayList<>();
        bulletType=BulletType.SMALL;
        lastShotTime=System.currentTimeMillis();
        this.entityFactory=entityFactory;
        this.playerId=playerId;
        this.coolDowns=coolDowns;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public void accept(EntityVisitor visitor){
        if (!visitors.contains(visitor)) visitors.add(visitor);
    }

    public void shoot(double x, double y, double angle){
        int minTime = coolDowns.get(bulletType);
        if (System.currentTimeMillis()-lastShotTime>=minTime){
            Projectile projectile= entityFactory.getProjectile(x,y,angle,bulletType,playerId);
            for (EntityVisitor visitor : visitors) {
                projectile.accept(visitor);
            }
            lastShotTime=System.currentTimeMillis();
        }
    }

    public BulletType getBulletType() {
        return bulletType;
    }
}
