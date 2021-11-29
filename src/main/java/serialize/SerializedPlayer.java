package serialize;

import controller.Movement;
import javafx.scene.input.KeyCode;
import misc.BulletType;
import misc.Player;
import model.concrete.Ship;

import java.io.Serializable;
import java.util.Map;

public class SerializedPlayer implements Serializable {
    private final String name;
    private final int id, points, lives;
    private final SerializedShip serializableShip;
    private final Map<KeyCode, Movement> keyBindings;
    private final BulletType bulletType;

    public SerializedPlayer(String name, int id, int points, int lives, SerializedShip serializableShip, BulletType bulletType, Map<KeyCode, Movement> keyBindings) {
        this.name = name;
        this.id = id;
        this.points = points;
        this.lives = lives;
        this.serializableShip = serializableShip;
        this.keyBindings = keyBindings;
        this.bulletType = bulletType;
    }

    public Player toPlayer(){
        Ship ship=serializableShip.toEntity();
        ship.setBulletType(bulletType);
        return new Player(name,lives,points,ship,keyBindings);
    }
}
