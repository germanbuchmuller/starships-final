package model.factory.concrete;

import engine.GameConfig;
import misc.BulletType;
import model.concrete.Projectile;
import model.factory.ProjectileFactory;

public class MyProjectileFactory extends AbstractEntityFactory<Projectile> implements ProjectileFactory {
    private final GameConfig gameConfig;
    private BulletType bulletType;
    private int playerId;

    public MyProjectileFactory(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Override
    public Projectile getEntity() {
        int damage = gameConfig.getBulletDamages().get(bulletType);
        double speed = gameConfig.getBulletSpeeds().get(bulletType);
        double width = gameConfig.getBulletWidth().get(bulletType);
        double height = gameConfig.getBulletHeight().get(bulletType);
        String texture = gameConfig.getBulletTextures().get(bulletType);
        return new Projectile(damage,1, speed,speed, x, y, angle, width, height, texture, playerId);
    }


    @Override
    public void setBulletType(BulletType bulletType) {
        this.bulletType=bulletType;
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
