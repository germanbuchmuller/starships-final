package model;

public abstract class AbstractEntity implements Entity{
    private int health;
    private final int maxSpeed, rewardPoints;
    private double x, y, angle;
    private final double width, height;
    private final String imageFileName;
    private boolean isDestroyed;


    public AbstractEntity(int health, int maxSpeed, int rewardPoints, double x, double y, double angle, double width, double height, String imageFileName) {
        this.health = health;
        this.maxSpeed = maxSpeed;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width=width;
        this.height=height;
        this.rewardPoints=rewardPoints;
        this.imageFileName=imageFileName;
        isDestroyed=false;
    }

    @Override
    public final double getX() {
        return x;
    }

    @Override
    public final double getY() {
        return y;
    }

    @Override
    public final double getAngle() {
        return angle;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public final void setPosition(double x, double y, double angle) {
        this.x=x;
        this.y=y;
        this.angle=angle;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getRewardPoints() {
        return rewardPoints;
    }

    @Override
    public String getImageFileName() {
        return imageFileName;
    }

    @Override
    public void destroy() {
        isDestroyed=true;
        System.out.println(this + "destroyed");
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
