package serialize;

import model.concrete.Asteroid;

public class SerializedAsteroid implements SerializedEntity {
    private final int health,maxSpeed,rewardPoints,damage;
    private final double x, y, angle, width, height;
    private final String imageFileName;

    public SerializedAsteroid(int health, int maxSpeed, int rewardPoints, int damage, double x, double y, double angle, double width, double height, String imageFileName) {
        this.health = health;
        this.maxSpeed = maxSpeed;
        this.rewardPoints = rewardPoints;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.imageFileName = imageFileName;
    }

    @Override
    public Asteroid toEntity() {
        return new Asteroid(damage,health,3,4,x,y,angle,width,height,imageFileName);
    }
}
