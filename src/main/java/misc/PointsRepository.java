package misc;

import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;

public interface PointsRepository {
    int getPoints(Ship ship);
    int getPoints(Asteroid asteroid);
    int getPoints(Projectile projectile);
}
