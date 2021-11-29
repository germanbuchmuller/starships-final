package misc;

import model.Asteroid;
import model.Projectile;
import model.Ship;

public class MyPointsRepository implements  PointsRepository{
    @Override
    public int getPoints(Ship ship) {
        return 200;
    }

    @Override
    public int getPoints(Asteroid asteroid) {
        return (int) asteroid.getWidth();
    }

    @Override
    public int getPoints(Projectile projectile) {
        return 1;
    }
}
