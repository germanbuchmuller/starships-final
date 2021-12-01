package model.factory.concrete;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import controller.collision.concrete.ShipCollider;
import misc.Weapon;
import misc.utils.Random;
import model.concrete.Ship;
import model.factory.ShipFactory;
import view.GameWindow;

public class MyShipFactory extends AbstractEntityFactory<Ship> implements ShipFactory {
    private final CollisionChecker collisionChecker;
    private final GameWindow gameWindow;
    private Weapon weapon;
    private int playerId;
    private boolean randomSpawn;
    private final Random random;

    public MyShipFactory(GameWindow gameWindow, CollisionChecker collisionChecker, Random random) {
        this.collisionChecker = collisionChecker;
        this.random = random;
        this.randomSpawn=false;
        this.gameWindow=gameWindow;
        x=0;
        y=0;
        angle=0;
    }

    @Override
    public Ship getEntity() {
        if (randomSpawn){
            Ship ship = new Ship(maxHealth, health,maxSpeed,acceleration, weapon,0,0,0,width,height,imageFileName,playerId);
            setShipSpawnPosition(ship);
            return ship;
        }else{
            return new Ship(maxHealth, health,maxSpeed,acceleration, weapon,x,y,angle,width,height,imageFileName,playerId);
        }
    }

    @Override
    public void reviveShip(Ship ship) {
        setShipSpawnPosition(ship);
        ship.revive();
    }

    private void setShipSpawnPosition(Ship ship){
        double x = randomShipX();
        double y = randomShipY();
        ship.setPosition(x,y,ship.getAngle());
        ShipCollider shipCollider = new ShipCollider(ship,null);
        while (newEntityCollidesWithColliders(shipCollider)){
            ship.setPosition(randomShipX(),randomShipY(),0);
            System.out.println("Created entity was colliding. Assigning new position...");
        }
    }

    private boolean newEntityCollidesWithColliders(EntityCollider entityCollider){
        return collisionChecker.isColliding(entityCollider);
    }

    private int randomShipX(){
        return random.get((int)width,(int)(gameWindow.getWidth()-width));
    }

    private int randomShipY(){
        return random.get((int)height,(int)(gameWindow.getHeight()-height));
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon=weapon;
    }


    @Override
    public void setPlayerId(int playerId) {
        this.playerId=playerId;
    }


    @Override
    public void setRandomSpawn(boolean randomSpawn) {
        this.randomSpawn=randomSpawn;
    }
}
