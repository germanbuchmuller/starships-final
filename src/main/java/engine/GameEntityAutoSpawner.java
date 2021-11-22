package engine;

import controller.visitor.EntityVisitor;
import javafx.scene.layout.Pane;
import model.Asteroid;
import org.jetbrains.annotations.NotNull;
import misc.utils.Random;

import java.util.ArrayList;
import java.util.List;

public class GameEntityAutoSpawner {
    private final List<EntityVisitor> visitors;
    private final List<Asteroid> asteroids;
    private final List<Asteroid> asteroidsToRemove;
    private final Pane pane;

    public GameEntityAutoSpawner(Pane pane) {
        this.visitors = new ArrayList<>();
        this.asteroidsToRemove = new ArrayList<>();
        asteroids=new ArrayList<>();
        this.pane=pane;
    }

    public void addVisitor(@NotNull EntityVisitor visitor){
        if (!visitors.contains(visitor))visitors.add(visitor);
    }

    public void update(){
        if (asteroids.size()<GameConfig.MIN_ASTEROIDS_IN_GAME){
            spawnAsteroid();
        }else if (asteroids.size()<GameConfig.MAX_ASTEROIDS_IN_GAME){
            if (Random.get(0,500)<15) spawnAsteroid();
        }
        for (Asteroid asteroid : asteroids) {
            if (asteroid.isDestroyed()){
                asteroidsToRemove.add(asteroid);
            }
        }
        asteroids.removeAll(asteroidsToRemove);
        asteroidsToRemove.clear();
    }

    private void spawnAsteroid(){
        int bound = Random.get(0,4);
        int scale = Random.get(1,6);
        int size = 50 * scale;
        int health = (int)(size*0.9);
        int damage = (int)(size*0.5);
        int maxSpeed = Random.get(50/scale,GameConfig.ASTEROID_SPEED);
        Asteroid spawnedAsteroid;
        switch (bound){
            case 0:
                spawnedAsteroid= new Asteroid(damage,health,maxSpeed,health,-2*size,Random.get(0,(int)pane.getLayoutBounds().getMaxY()),Random.get(20,160),size,size,"asteroid.png");
                break;
            case 1:
                spawnedAsteroid= new Asteroid(damage,health,maxSpeed,health,Random.get(0,(int)pane.getLayoutBounds().getMaxX()),-2*size,Random.get(100,250),size,size,"asteroid.png");
                break;
            case 2:
                spawnedAsteroid= new Asteroid(damage,health,maxSpeed,health,pane.getLayoutBounds().getMaxX()+2*size,Random.get(0,(int)pane.getLayoutBounds().getMaxY()),Random.get(-160,-20),size,size,"asteroid.png");
                break;
            default:
                spawnedAsteroid= new Asteroid(damage,health,maxSpeed,health,Random.get(0,(int)pane.getLayoutBounds().getMaxX()),pane.getLayoutBounds().getMaxY()+2*size,Random.get(-65,65),size,size,"asteroid.png");
                break;
        }
        for (EntityVisitor visitor : visitors) {
            spawnedAsteroid.accept(visitor);
        }
        asteroids.add(spawnedAsteroid);
    }

}
