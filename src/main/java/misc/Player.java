package misc;

import controller.Movement;
import controller.visitor.EntityVisitor;
import engine.concrete.MyGameConfig;
import javafx.scene.input.KeyCode;
import model.concrete.Ship;
import misc.utils.Random;

import java.util.Map;

public class Player {
    private final String name;
    private final int id;
    private int points;
    private int lives;
    private Ship ship;
    private Map<KeyCode, Movement> keyBindings;

    public Player(String name, int lives, int points,Ship ship, Map<KeyCode, Movement> keyBindings) {
        this.name = name;
        this.id = ship.getPlayerId();
        this.points = points;
        this.keyBindings = keyBindings;
        this.ship=ship;
        this.lives=lives;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setKeyBindings(Map<KeyCode, Movement> keyBindings) {
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

    public int getLives() {
        return lives;
    }

    public boolean revive(){
        if (lives>1){
            lives--;
            return true;
        }else{
            lives=0;
            return false;
        }
    }

}
