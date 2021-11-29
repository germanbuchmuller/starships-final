package misc;

import model.Asteroid;
import model.Projectile;
import model.Ship;

public interface PointsRepository {
    int getPoints(Ship ship);
    int getPoints(Asteroid asteroid);
    int getPoints(Projectile projectile);
}
