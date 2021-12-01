package model.factory.concrete;

import controller.collision.CollisionChecker;
import controller.collision.EntityCollider;
import controller.collision.concrete.AsteroidCollider;
import misc.utils.Random;
import model.concrete.Asteroid;
import model.factory.AsteroidFactory;
import view.GameWindow;

public class MyAsteroidFactory extends AbstractEntityFactory<Asteroid> implements AsteroidFactory {
    private final CollisionChecker collisionChecker;
    private final GameWindow gameWindow;
    private boolean randomSpawn;
    private Random random;

    public MyAsteroidFactory( CollisionChecker collisionChecker, GameWindow gameWindow, Random random) {
        this.collisionChecker = collisionChecker;
        this.gameWindow = gameWindow;
        this.random = random;
        randomSpawn=false;
    }

    @Override
    public void setRandomSpawn(boolean randomSpawn) {
        this.randomSpawn=true;
    }


    @Override
    public Asteroid getEntity() {
        if (randomSpawn){
            final int size = random.get(50,400);
            int[] pos = randomAsteroidPos(size);
            Asteroid asteroid = new Asteroid((int)(size*0.5),size,500/size,2,pos[0],pos[1],pos[2],size,size,imageFileName);
            setAsteroidSpawnPosition(asteroid);
            return asteroid;
        }else{
            return new Asteroid((int)(width*0.5),(int)width,500/width,2,x,y,angle,width,height,imageFileName);
        }
    }

    private void setAsteroidSpawnPosition(Asteroid asteroid){
        int[] pos ;
        AsteroidCollider asteroidCollider = new AsteroidCollider(asteroid,null);
        while (newEntityCollidesWithColliders(asteroidCollider)){
            pos = randomAsteroidPos(asteroid.getHealth());
            asteroid.setPosition(pos[0],pos[1],pos[2]);
            System.out.println("Created asteroid was colliding. Assigning new position...");
        }
    }

    private boolean newEntityCollidesWithColliders(EntityCollider entityCollider){
        return collisionChecker.isColliding(entityCollider);
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
            pos[1]=(int)(gameWindow.getHeight()+size);
            pos[2]=random.get(-30,30);
            //pos[2]=0;
        }
        return pos;
    }
}
