package serialize.concrete;

import controller.Movement;
import javafx.scene.input.KeyCode;
import misc.BulletType;
import misc.Player;
import model.concrete.Ship;

import java.io.Serializable;
import java.util.Map;

public class SerializedPlayer implements Serializable {
    private final String name;
    private final int  points, lives;
    private final SerializedShip serializedShip;
    private final Map<KeyCode, Movement> keyBindings;

    public SerializedPlayer(Player player){
        this.name=player.getName();
        this.points=player.getPoints();
        this.lives=player.getLives();
        this.keyBindings=player.getKeyBindings();
        this.serializedShip=new SerializedShip(player.getShip());
    }

    public Player toPlayer(){
        Ship ship=serializedShip.toEntity();
        return new Player(name,lives,points,ship,keyBindings);
    }

    public BulletType getBulletType(){
        return serializedShip.getBulletType();
    }
}
