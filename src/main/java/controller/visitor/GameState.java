package controller.visitor;

import model.*;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.RenderEngine;

import java.util.List;

public interface GameState extends GameEngineVisitor {
    List<Entity> getEntities();
    List<Ship> getShips();
    List<Asteroid> getAsteroids();
    List<Projectile> getProjectiles();
    List<SelfMovable> getSelfMovables();
    void reject(Ship ship);
    void reject(Asteroid asteroid);
    void reject(Projectile projectile);
}
