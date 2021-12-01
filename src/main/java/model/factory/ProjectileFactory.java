package model.factory;

import misc.BulletType;
import model.concrete.Projectile;

public interface ProjectileFactory extends EntityFactory<Projectile> {
    void setBulletType(BulletType bulletType);
    void setPlayerId(int playerId);
}
