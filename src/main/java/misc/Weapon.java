package misc;

import controller.visitor.EntityVisitor;

public interface Weapon {
    void setBulletType(BulletType bulletType);
    void accept(EntityVisitor visitor);
    void shoot(double x, double y, double angle);
    BulletType getBulletType();
}
