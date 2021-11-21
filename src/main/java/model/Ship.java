package model;

import org.jetbrains.annotations.NotNull;
import controller.visitor.EntityVisitor;

public class Ship extends AbstractEntity{
    private final int playerID;

    public Ship(int health, int maxSpeed, int rewardPoints, double x, double y, double angle, double width, double height,String imageFileName, int playerID) {
       super(health,maxSpeed,rewardPoints,x,y,angle,width,height, imageFileName);
       this.playerID=playerID;
    }

    @Override
    public EntityType getType() {
        return EntityType.SHIP;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void accept(@NotNull EntityVisitor visitor){
        visitor.visit(this);
    }
}
