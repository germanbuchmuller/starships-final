package model.factory;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import controller.collision.concrete.AsteroidCollider;
import controller.collision.concrete.ShipCollider;
import engine.GameConfig;
import engine.concrete.MyGameConfig;
import misc.BulletType;
import misc.Weapon;
import misc.utils.Random;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.GameWindow;

import java.util.ArrayList;
import java.util.Collection;

public class MyEntityFactory implements EntityFactory {
    private final GameConfig gameConfig;
    private final Collection<EntityCollider> colliders;
    private final CollisionChecker collisionChecker;
    private final GameWindow gameWindow;
    private final Random random;

    public MyEntityFactory(GameConfig gameConfig, Collection<EntityCollider> colliders, CollisionChecker collisionChecker, GameWindow gameWindow, Random random) {
        this.gameConfig = gameConfig;
        this.colliders = colliders;
        this.collisionChecker = collisionChecker;
        this.gameWindow = gameWindow;
        this.random=random;
    }

    @Override
    public Ship getShip(Weapon weapon, double x, double y, String imageFileName, int playerId) {
        return new Ship(gameConfig.getShipMaxHealth(),gameConfig.getShipMaxSpeed(),gameConfig.getShipAcceleration(),weapon,x,y,0,gameConfig.getShipWidth(),gameConfig.getShipHeight(),imageFileName,playerId);
    }

    @Override
    public Ship getShip(Weapon weapon,String imageFileName, int playerId) {
        Ship ship = new Ship(gameConfig.getShipMaxHealth(),gameConfig.getShipMaxSpeed(),gameConfig.getShipAcceleration(), weapon,0,0,0,gameConfig.getShipWidth(),gameConfig.getShipHeight(),imageFileName,playerId);
        setShipSpawnPosition(ship);
        return ship;
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

    @Override
    public Asteroid getAsteroid() {
        final int size = random.get(50,400);
        int[] pos = randomAsteroidPos(size);
        Asteroid asteroid = new Asteroid((int)(size*0.5),size,500/size,2,pos[0],pos[1],pos[2],size,size,gameConfig.getAsteroidsTexture());
        AsteroidCollider asteroidCollider = new AsteroidCollider(asteroid,null);
        while (newEntityCollidesWithColliders(asteroidCollider)){
            pos = randomAsteroidPos(size);
            asteroid.setPosition(pos[0],pos[1],pos[2]);
            System.out.println("Created entity was colliding. Assigning new position...");
        }
        return asteroid;
    }

    @Override
    public Projectile getProjectile(double x, double y, double angle, BulletType bulletType, int playerId) {
        int damage = gameConfig.getBulletDamages().get(bulletType);
        double speed = gameConfig.getBulletSpeeds().get(bulletType);
        double width = gameConfig.getBulletWidth().get(bulletType);
        double height = gameConfig.getBulletHeight().get(bulletType);
        String texture = gameConfig.getBulletTextures().get(bulletType);
        return new Projectile(damage,1, speed,5, x, y, angle, width, height, texture, playerId);
    }

    private boolean newEntityCollidesWithColliders(EntityCollider entityCollider){
        return collisionChecker.isColliding(entityCollider,new ArrayList<>(colliders));
    }

    private int randomShipX(){
        return random.get((int)gameConfig.getShipWidth(),(int)(gameWindow.getWidth()-gameConfig.getShipWidth()));
    }

    private int randomShipY(){
        return random.get((int)gameConfig.getShipHeight(),(int)(gameWindow.getHeight()-gameConfig.getShipHeight()));
    }

    private int[] randomAsteroidPos(int size){
        int[] pos = new int[3];
        int bound = random.get(0,4);
        if (bound==0){
            pos[0]=-size*2;
            pos[1]=random.get(100,(int)(gameWindow.getHeight()-100));
            pos[2]=random.get(60,120);
            //pos[2]=90;
        }else if (bound==1){
            pos[0]=(int)(gameWindow.getWidth()+size);
            pos[1]=random.get(100,(int)(gameWindow.getHeight()-100));
            pos[2]=random.get(-120,-60);
            //pos[2]=-90;
        }else if (bound==2){
            pos[0]=random.get(100,(int)(gameWindow.getWidth()-100));
            pos[1]=-size*2;
            pos[2]=random.get(150,210);
            //pos[2]=180;
        }else{
            pos[0]=random.get(100,(int)(gameWindow.getWidth()-100));
            pos[1]=(int)(gameWindow.getHeight()+size);;
            pos[2]=random.get(-30,30);
            //pos[2]=0;
        }
        return pos;
    }
}
