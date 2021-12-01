package serialize;

import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Projectile;

public class SerializedProjectile extends AbstractSerializedEntity {
    private final int damage,playerID;

    public SerializedProjectile(Projectile projectile) {
        super(projectile);
        this.damage=projectile.getDamage();
        this.playerID=projectile.getPlayerId();
    }

    public Projectile toEntity() {
        Projectile projectile= new Projectile(damage,health,maxSpeed,acceleration,x,y,angle,width,height,imageFileName,playerID);
        projectile.setMovementDirection(Vector2.vector(vectorX,vectorY));
        return projectile;
    }
}
