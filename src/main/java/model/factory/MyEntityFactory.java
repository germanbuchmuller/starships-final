package model.factory;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import controller.collision.concrete.AsteroidCollider;
import controller.collision.concrete.ShipCollider;
import engine.GameConfig;
import misc.utils.Random;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import view.GameWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyEntityFactory implements EntityFactory {
    private final GameConfig gameConfig;
    private final Collection<EntityCollider> colliders;
    private final CollisionChecker collisionChecker;
    private final GameWindow gameWindow;

    public MyEntityFactory(GameConfig gameConfig, Collection<EntityCollider> colliders, CollisionChecker collisionChecker, GameWindow gameWindow) {
        this.gameConfig = gameConfig;
        this.colliders = colliders;
        this.collisionChecker = collisionChecker;
        this.gameWindow = gameWindow;
    }

    @Override
    public Ship getShip(double x, double y, String imageFileName, int playerId) {
        return new Ship(gameConfig.getShipMaxHealth(),gameConfig.getShipMaxSpeed(),gameConfig.getShipAcceleration(),x,y,0,gameConfig.getShipWidth(),gameConfig.getShipHeight(),imageFileName,playerId);
    }

    @Override
    public Ship getShip(String imageFileName, int playerId) {
        double x = randomShipX();
        double y = randomShipY();
        Ship ship = new Ship(gameConfig.getShipMaxHealth(),gameConfig.getShipMaxSpeed(),gameConfig.getShipAcceleration(),x,y,0,gameConfig.getShipWidth(),gameConfig.getShipHeight(),imageFileName,playerId);
        ShipCollider shipCollider = new ShipCollider(ship,null);
        while (newEntityCollidesWithColliders(shipCollider)){
            ship.setPosition(randomShipX(),randomShipY(),0);
            System.out.println("Created entity was colliding. Assigning new position...");
        }
        return ship;
    }

    @Override
    public Asteroid getAsteroid() {
        final int size = Random.get(50,400);
        int[] pos = randomAsteroidPos(size);
        Asteroid asteroid = new Asteroid((int)(size*0.5),size,500/size,2,pos[0],pos[1],pos[2],size,size,gameConfig.getAsteroidsTexture());
        AsteroidCollider asteroidCollider = new AsteroidCollider(asteroid,null);
        while (newEntityCollidesWithColliders(asteroidCollider)){
            pos = randomAsteroidPos(size);
            asteroid.setPosition(pos[0],pos[1],pos[2]);
            System.out.println("Created entity was colliding. Assigning new position...");
        }
        System.out.println(asteroid.getX()+" "+asteroid.getY()+" "+asteroid.getAngle());
        return asteroid;
    }

    @Override
    public Projectile getProjectile() {
        return null;
    }

    private boolean newEntityCollidesWithColliders(EntityCollider entityCollider){
        return collisionChecker.isColliding(entityCollider,new ArrayList<>(colliders));
    }

    private int randomShipX(){
        return Random.get((int)gameConfig.getShipWidth(),(int)(gameWindow.getWidth()-gameConfig.getShipWidth()));
    }

    private int randomShipY(){
        return Random.get((int)gameConfig.getShipHeight(),(int)(gameWindow.getHeight()-gameConfig.getShipHeight()));
    }

    private int[] randomAsteroidPos(int size){
        int[] pos = new int[3];
        int bound = Random.get(0,4);
        if (bound==0){
            pos[0]=-size*2;
            pos[1]=Random.get(100,(int)(gameWindow.getHeight()-100));
            //pos[2]=Random.get(20,160);
            pos[2]=90;
        }else if (bound==1){
            pos[0]=(int)(gameWindow.getWidth()+size);
            pos[1]=Random.get(100,(int)(gameWindow.getHeight()-100));
            //pos[2]=Random.get(-160,-20);
            pos[2]=-90;
        }else if (bound==2){
            pos[0]=Random.get(100,(int)(gameWindow.getWidth()-100));
            pos[1]=-size*2;
            //pos[2]=Random.get(100,250);
            pos[2]=180;
        }else{
            pos[0]=Random.get(100,(int)(gameWindow.getWidth()-100));
            pos[1]=(int)(gameWindow.getHeight()+size);;
            //pos[2]=Random.get(-250,-100);
            pos[2]=0;
        }
        return pos;
    }
}
