package serialize;

import edu.austral.dissis.starships.vector.Vector2;
import model.concrete.Asteroid;

public class SerializedAsteroid extends AbstractSerializedEntity {
    private final int damage;

    public SerializedAsteroid(Asteroid asteroid) {
        super(asteroid);
        this.damage=asteroid.getDamage();
    }

    @Override
    public Asteroid toEntity() {
        Asteroid asteroid =new Asteroid(damage,health,maxSpeed,acceleration,x,y,angle,width,height,imageFileName);
        asteroid.setMovementDirection(Vector2.vector(vectorX,vectorY));
        return asteroid;
    }
}
