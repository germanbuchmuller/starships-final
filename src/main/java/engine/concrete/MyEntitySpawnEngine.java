package engine.concrete;

import controller.visitor.GameState;
import engine.EntitySpawnEngine;
import engine.GameConfig;
import misc.utils.Random;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import model.factory.AsteroidFactory;
import org.jetbrains.annotations.NotNull;

public class MyEntitySpawnEngine implements EntitySpawnEngine {
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final AsteroidFactory asteroidFactory;
    private final Random random;

    public MyEntitySpawnEngine(@NotNull GameState gameState, @NotNull GameConfig gameConfig, @NotNull AsteroidFactory entityFactory, @NotNull Random random) {
        this.gameState = gameState;
        this.gameConfig=gameConfig;
        this.asteroidFactory=entityFactory;
        asteroidFactory.setRandomSpawn(true);
        asteroidFactory.setImageFileName(gameConfig.getAsteroidsTexture());
        this.random=random;
    }

    @Override
    public void update() {
        checkIfNewAsteroidNeeded();
    }

    private void checkIfNewAsteroidNeeded() {
        int asteroidsInGame = gameState.getAsteroids().size();
        if (asteroidsInGame<gameConfig.getMinAsteroidsInGame()){
            spawnAsteroid();
        }else if (asteroidsInGame<gameConfig.getMaxAsteroidsInGame()){
            if (random.get(0,1000)<15)spawnAsteroid();
        }
    }

    private void spawnAsteroid() {
        gameState.visit(asteroidFactory.getEntity());
    }

    @Override
    public void added(Ship ship) {}

    @Override
    public void added(Asteroid asteroid) {}

    @Override
    public void added(Projectile projectile) {}

    @Override
    public void removed(Ship ship) {}

    @Override
    public void removed(Asteroid asteroid) {
        spawnAsteroid();
    }

    @Override
    public void removed(Projectile projectile) {}

}
