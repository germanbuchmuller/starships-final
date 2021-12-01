package controller.visitor.concrete;

import controller.MovementEngine;
import controller.collision.CollisionsEngine;
import controller.visitor.GameState;
import core.GameEngine;
import misc.Player;
import model.*;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.RenderEngine;

import java.util.ArrayList;
import java.util.List;

public class MyGameState implements GameState {
    private final List<Entity> entities;
    private final List<Asteroid> asteroids;
    private final List<Projectile> projectiles;
    private final List<Ship> ships;
    private final List<GameEngine> gameEngines;
    private final List<Player> players;

    public MyGameState(List<Entity> entities, List<Asteroid> asteroids, List<Projectile> projectiles, List<Ship> ships, List<Player> players) {
        this.entities = entities;
        this.asteroids = asteroids;
        this.projectiles = projectiles;
        this.ships = ships;
        this.players=players;
        this.gameEngines=new ArrayList<>();
    }

    public MyGameState() {
        entities=new ArrayList<>();
        asteroids=new ArrayList<>();
        projectiles=new ArrayList<>();
        ships=new ArrayList<>();
        players=new ArrayList<>();
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
        addToEngines(asteroid);
    }

    @Override
    public void visit(Projectile projectile) {
        entities.add(projectile);
        projectiles.add(projectile);
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
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void addPlayer(Player player) {
        if (!players.contains(player)){
            players.add(player);
        }
    }

    @Override
    public void reject(Ship ship) {
        entities.remove(ship);
        ships.remove(ship);
        removeFromEngines(ship);
    }

    @Override
    public void reject(Asteroid asteroid) {
        System.out.println(asteroid+" destroyed");
        entities.remove(asteroid);
        asteroids.remove(asteroid);
        removeFromEngines(asteroid);
    }

    @Override
    public void reject(Projectile projectile) {
        System.out.println(projectile+" destroyed");
        entities.remove(projectile);
        projectiles.remove(projectile);
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
            gameEngine.removed(ship);
        }
    }

    private void removeFromEngines(Asteroid asteroid){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.removed(asteroid);
        }
    }

    private void removeFromEngines(Projectile projectile){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.removed(projectile);
        }
    }

    private void addToEngines(Ship ship){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.added(ship);
        }
    }

    private void addToEngines(Asteroid asteroid){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.added(asteroid);
        }
    }

    private void addToEngines(Projectile projectile){
        for (GameEngine gameEngine : gameEngines) {
            gameEngine.added(projectile);
        }
    }
}
