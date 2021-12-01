package misc.concrete;

import controller.visitor.EntityVisitor;
import misc.BulletType;
import misc.Weapon;
import model.concrete.Projectile;
import model.factory.ProjectileFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWeapon implements Weapon {
    private final Map<BulletType, Integer> coolDowns;
    private final List<EntityVisitor> visitors;
    private final ProjectileFactory projectilFactory;
    private final int playerId;
    private BulletType bulletType;
    private long lastShotTime;


    public MyWeapon(@NotNull Map<BulletType, Integer> coolDowns, @NotNull ProjectileFactory projectilFactory, int playerId) {
        visitors=new ArrayList<>();
        bulletType=BulletType.SMALL;
        lastShotTime=System.currentTimeMillis();
        this.projectilFactory=projectilFactory;
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
            projectilFactory.setPosition(x,y,angle);
            projectilFactory.setBulletType(bulletType);
            projectilFactory.setPlayerId(playerId);
            Projectile projectile= projectilFactory.getEntity();
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
