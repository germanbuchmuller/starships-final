package serialize;

import edu.austral.dissis.starships.vector.Vector2;
import misc.BulletType;
import model.concrete.Ship;

public class SerializedShip extends AbstractSerializedEntity{
    private final int playerID;
    private final BulletType bulletType;

    public SerializedShip(Ship ship) {
        super(ship);
        this.playerID=ship.getPlayerId();
        this.bulletType=ship.getBulletType();
    }

    @Override
    public Ship toEntity() {
        Ship ship = new Ship(maxHealth,health,maxSpeed,acceleration,null,x,y,angle,width,height,imageFileName,playerID);
        ship.setMovementDirection(Vector2.vector(vectorX,vectorY));
        return ship;
    }

    public BulletType getBulletType(){
        return bulletType;
    }
}
