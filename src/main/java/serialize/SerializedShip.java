package serialize;

import model.concrete.Ship;

public class SerializedShip implements SerializedEntity{
    private final int health,maxSpeed,rewardPoints,playerID;
    private final double x, y, angle, width, height;
    private final String imageFileName;

    public SerializedShip(int health, int maxSpeed, int rewardPoints, int playerID, double x, double y, double angle, double width, double height, String imageFileName) {
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
    public Ship toEntity() {
        return new Ship(health,2.5,x,y,angle,width,height,imageFileName,playerID);
    }
}
