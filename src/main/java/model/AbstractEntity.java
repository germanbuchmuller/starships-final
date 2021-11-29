package model;

import edu.austral.dissis.starships.vector.Vector2;

public abstract class AbstractEntity implements Entity{
    private int health;
    private final int maxHealth;
    private Vector2 movementDirection;
    private double x, y, angle;
    private final double width, height;
    private final String imageFileName;
    private boolean isDestroyed;
    private boolean accelerating;

    public AbstractEntity(int maxHealth, double x, double y, double angle, double width, double height, String imageFileName) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.movementDirection = Vector2.vectorFromModule(0,0);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width=width;
        this.height=height;
        this.imageFileName=imageFileName;
        isDestroyed=false;
        accelerating=false;
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
    public final double getWidth() {
        return width;
    }

    @Override
    public final double getHeight() {
        return height;
    }

    @Override
    public final void setPosition(double x, double y, double angle) {
        this.x=x;
        this.y=y;
        this.angle=angle;
    }

    @Override
    public Vector2 getMovementDirection() {
        return movementDirection;
    }

    @Override
    public void setMovementDirection(Vector2 movementDirection) {
        this.movementDirection = movementDirection;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public String getImageFileName() {
        return imageFileName;
    }

    @Override
    public void destroy() {
        isDestroyed=true;
        setMovementDirection(Vector2.vector(0,0));
        setAccelerating(false);
        System.out.println(this + " destroyed");
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void revive(){
        this.isDestroyed=false;
        health=maxHealth;
    }

    @Override
    public void harm(int amount) {
        health-=amount;
        if (health<0){
            health=0;
        }
    }

    @Override
    public void heal(int amount) {
        health+=amount;
        if (health>maxHealth) health=maxHealth;
    }

    @Override
    public boolean isAlive() {
        return health>0;
    }

    @Override
    public final boolean isAccelerating() {
        return accelerating;
    }

    @Override
    public final void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }
}
