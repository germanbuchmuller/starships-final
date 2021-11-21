package player;

import model.Ship;

public class Player {
    private String name;
    private int id;
    private int points;
    private Ship ship;

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
}
