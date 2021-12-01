package engine.concrete;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import controller.visitor.GameState;
import edu.austral.dissis.starships.collision.Collider;
import engine.EntitySpawnEngine;
import engine.GameConfig;
import misc.utils.Random;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import model.factory.EntityFactory;
import model.factory.MyEntityFactory;
import org.jetbrains.annotations.NotNull;
import view.GameWindow;

import java.util.Collection;
import java.util.List;

public class MyEntitySpawnEngine implements EntitySpawnEngine {
    private final GameState gameState;
    private final GameConfig gameConfig;
    private final EntityFactory entityFactory;
    private final Random random;

    public MyEntitySpawnEngine(@NotNull GameState gameState, @NotNull GameConfig gameConfig, @NotNull EntityFactory entityFactory, @NotNull Random random) {
        this.gameState = gameState;
        this.gameConfig=gameConfig;
        this.entityFactory=entityFactory;
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
        gameState.visit(entityFactory.getAsteroid());
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
