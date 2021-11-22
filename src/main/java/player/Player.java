package player;

import controller.Movement;
import controller.visitor.EntityVisitor;
import engine.GameConfig;
import javafx.scene.input.KeyCode;
import model.Ship;
import utils.Random;

import java.util.Map;

public class Player {
    private String name;
    private int id;
    private int points;
    private int lives;
    private Ship ship;
    private Map<KeyCode, Movement> keyBindings;

    public Player(String name, int id, int lives, int points,String shipTexture, Map<KeyCode, Movement> keyBindings) {
        this.name = name;
        this.id = id;
        this.points = points;
        this.lives=lives;
        this.ship=new Ship(GameConfig.SHIP_HEALTH, GameConfig.SHIP_SPEED,GameConfig.SHIP_REWARD_POINTS, Random.get(0,500),Random.get(0,500),0,GameConfig.SHIP_WIDTH,GameConfig.SHIP_HEIGHT,shipTexture,id);
        this.keyBindings = keyBindings;
    }
    public Player(String name, int id, int lives, int points,Ship ship, Map<KeyCode, Movement> keyBindings) {
        this.name = name;
        this.id = id;
        this.points = points;
        this.keyBindings = keyBindings;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public void addVisitor(EntityVisitor entityVisitor){
        ship.accept(entityVisitor);
    }

    public Map<KeyCode, Movement> getKeyBindings() {
        return keyBindings;
    }

    public Ship getShip() {
        return ship;
    }
}
