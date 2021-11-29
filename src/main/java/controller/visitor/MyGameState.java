package controller.visitor;

import controller.MovementEngine;
import controller.collision.CollisionsEngine;
import engine.GameEngine;
import model.*;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.RenderEngine;

import java.util.ArrayList;
import java.util.List;

public class MyGameState implements GameState{
    private final List<Entity> entities;
    private final List<SelfMovable> selfMovables;
    private final List<Asteroid> asteroids;
    private final List<Projectile> projectiles;
    private final List<Ship> ships;
    private final List<GameEngine> gameEngines;

    public MyGameState(List<Entity> entities, List<SelfMovable> selfMovables, List<Asteroid> asteroids, List<Projectile> projectiles, List<Ship> ships) {
        this.entities = entities;
        this.selfMovables = selfMovables;
        this.asteroids = asteroids;
        this.projectiles = projectiles;
        this.ships = ships;
        this.gameEngines=new ArrayList<>();
    }

    public MyGameState() {
        entities=new ArrayList<>();
        selfMovables=new ArrayList<>();
        asteroids=new ArrayList<>();
        projectiles=new ArrayList<>();
        ships=new ArrayList<>();
        this.gameEngines=new ArrayList<>();
    }

    @Override
    public void visit(Ship ship) {
        entities.add(ship);
        ships.add(ship);
        addToEngines(ship);
    }

    @Override
    public void visit(Asteroid asteroid) {
        entities.add(asteroid);
        asteroids.add(asteroid);
        selfMovables.add(asteroid);
        addToEngines(asteroid);
    }

    @Override
    public void visit(Projectile projectile) {
        entities.add(projectile);
        projectiles.add(projectile);
        selfMovables.add(projectile);
        addToEngines(projectile);
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public List<Ship> getShips() {
        return ships;
    }

    @Override
    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    @Override
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public List<SelfMovable> getSelfMovables() {
        return selfMovables;
    }

    @Override
    public void remove(Ship ship) {
        ship.destroy();
        entities.remove(ship);
        ships.remove(ship);
        removeFromEngines(ship);
    }

    @Override
    public void remove(Asteroid asteroid) {
        asteroid.destroy();
        entities.remove(asteroid);
        asteroids.remove(asteroid);
        selfMovables.remove(asteroid);
        removeFromEngines(asteroid);
    }

    @Override
    public void remove(Projectile projectile) {
        projectile.destroy();
        entities.remove(projectile);
        projectiles.remove(projectile);
        selfMovables.remove(projectile);
        removeFromEngines(projectile);
    }

    @Override
    public void visit(GameEngine gameEngine) {
        if (!gameEngines.contains(gameEngine)){
            gameEngines.add(gameEngine);
        }
    }

    @Override
    public void visit(RenderEngine renderEngine) {
        if (!gameEngines.contains(renderEngine)){
            gameEngines.add(renderEngine);
        }
    }

    @Override
    public void visit(MovementEngine movementEngine) {
        if (!gameEngines.contains(movementEngine)){
            gameEngines.add(movementEngine);
        }
    }

    @Override
    public void visit(CollisionsEngine collisionsEngine) {
        if (!gameEngines.contains(collisionsEngine)){
            gameEngines.add(collisionsEngine);
        }
    }

    private void removeFromEngines(Ship ship){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.remove(ship);
        }
    }

    private void removeFromEngines(Asteroid asteroid){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.remove(asteroid);
        }
    }

    private void removeFromEngines(Projectile projectile){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.remove(projectile);
        }
    }

    private void addToEngines(Ship ship){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.add(ship);
        }
    }

    private void addToEngines(Asteroid asteroid){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.add(asteroid);
        }
    }

    private void addToEngines(Projectile projectile){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.add(projectile);
        }
    }
}
