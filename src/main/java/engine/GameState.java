package engine;

import model.Asteroid;
import model.Entity;
import model.Projectile;
import model.SelfMovable;

import java.util.List;

public class GameState {
    public final List<Entity> entities;
    public final List<SelfMovable> selfMovables;
    public final List<Asteroid> asteroids;
    public final List<Projectile> projectiles;

    public GameState(List<Entity> entities, List<SelfMovable> selfMovables, List<Asteroid> asteroids, List<Projectile> projectiles) {
        this.entities = entities;
        this.selfMovables = selfMovables;
        this.asteroids = asteroids;
        this.projectiles = projectiles;
    }
}
