package model.factory;

import model.concrete.Asteroid;

public interface AsteroidFactory extends EntityFactory<Asteroid> {
    void setRandomSpawn(boolean randomSpawn);

}
