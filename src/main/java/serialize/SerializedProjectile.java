package serialize;

import model.Projectile;

public class SerializedProjectile implements SerializedEntity {
    private final int damage, health, maxSpeed, rewardPoints, playerID;
    private final double x, y, angle, width, height;
    private final String imageFileName;

    public SerializedProjectile(int damage, int health, int maxSpeed, int rewardPoints, int playerID, double x, double y, double angle, double width, double height, String imageFileName) {
        this.damage = damage;
        this.health = health;
        this.maxSpeed = maxSpeed;
        this.rewardPoints = rewardPoints;
        this.playerID = playerID;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.imageFileName = imageFileName;
    }

    @Override
    public Projectile toEntity() {
        return new Projectile(damage,health,maxSpeed,rewardPoints,x,y,angle,width,height,playerID,imageFileName);
    }
}
