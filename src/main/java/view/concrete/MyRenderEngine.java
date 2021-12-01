package view.concrete;

import controller.visitor.GameState;
import model.Entity;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;
import view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRenderEngine implements RenderEngine {
    private final GameState gameState;
    private final GameWindow gameWindow;
    private final EntityImageRepository entityImageRepository;
    private final Map<Entity, EntityView> entityViewMap;

    public MyRenderEngine(@NotNull GameState gameState, @NotNull GameWindow gameWindow, @NotNull EntityImageRepository entityImageRepository) {
        this.gameState = gameState;
        this.gameWindow=gameWindow;
        this.entityImageRepository=entityImageRepository;
        entityViewMap=new HashMap<>();
        gameState.visit(this);
    }

    public void update() {
        updateAsteroids();
        updateProjectiles();
        updateShips();
    }

    private void updateAsteroids(){
        List<Asteroid> asteroidsToRemove=new ArrayList<>();
        for (Asteroid asteroid : gameState.getAsteroids()) {
            if (isOutOfGameWindow(asteroid)){
                asteroidsToRemove.add(asteroid);
            }else{
                updateView(asteroid);
            }
        }
        for (Asteroid asteroid : asteroidsToRemove) {
            gameState.reject(asteroid);
        }
    }

    private void updateProjectiles(){
        List<Projectile> projectilesToRemove=new ArrayList<>();
        for (Projectile projectile : gameState.getProjectiles()) {
            if (isOutOfGameWindow(projectile)){
                projectilesToRemove.add(projectile);
            }else{
                updateView(projectile);
            }
        }
        for (Projectile projectile : projectilesToRemove) {
            gameState.reject(projectile);
        }
    }

    private void updateShips(){
        for (Ship ship : gameState.getShips()) {
            handleShipWindowBounds(ship);
            updateView(ship);
        }
    }

    private void updateView(Entity entity){
        entityViewMap.get(entity).update();
    }

    private boolean isOutOfGameWindow(Entity entity){
        double entitySize = (entity.getWidth()+entity.getHeight())*3;
        return (entity.getX()+entitySize<0) || (entity.getY()+entitySize<0) ||
                (entity.getX()-entitySize>gameWindow.getWidth()) ||
                (entity.getY()-entitySize>gameWindow.getHeight());
    }

    private void handleShipWindowBounds(Ship ship){
        double shipSize;
        if (ship.getWidth()>ship.getHeight())shipSize=ship.getWidth(); else shipSize=ship.getHeight();
        if (ship.getX()<=0){
            updateShipPosition(ship,0,ship.getY());
        }else if (ship.getX()+shipSize>=gameWindow.getWidth()){
            updateShipPosition(ship,gameWindow.getWidth()-shipSize,ship.getY());
        }else if (ship.getY()<=0){
            updateShipPosition(ship,ship.getX(),0);
        }else if (ship.getY()+shipSize>=gameWindow.getHeight()){
            updateShipPosition(ship,ship.getX(),gameWindow.getHeight()-shipSize);
        }
    }

    private void updateShipPosition(Ship ship, double x, double y){
        ship.setPosition(x,y,ship.getAngle());
        ship.setMovementDirection(ship.getMovementDirection().multiply(-0.3));
    }

    private void removeEntity(Entity entity){
        if (entityViewMap.containsKey(entity)){
            gameWindow.removeView(entityViewMap.get(entity));
            entityViewMap.remove(entity);
        }
    }

    private void addEntityOnTop(Entity entity){
        addNewViewOnTop(new MyEntityView(entity,entityImageRepository.getImage(entity)));
    }

    private void addEntityOnBack(Entity entity){
        addNewViewOnBack(new MyEntityView(entity,entityImageRepository.getImage(entity)));
    }

    private void addNewViewOnTop(EntityView entityView){
        gameWindow.addViewOnTop(entityView);
        entityViewMap.put(entityView.getEntity(),entityView);
    }

    private void addNewViewOnBack(EntityView entityView){
        gameWindow.addViewOnBack(entityView);
        entityViewMap.put(entityView.getEntity(),entityView);
    }

    @Override
    public void added(Ship ship) {
        addEntityOnBack(ship);
    }

    @Override
    public void added(Asteroid asteroid) {
        addEntityOnTop(asteroid);
    }

    @Override
    public void added(Projectile projectile) {
        addEntityOnBack(projectile);
    }

    @Override
    public void removed(Ship ship) {
        removeEntity(ship);
    }

    @Override
    public void removed(Asteroid asteroid) {
        removeEntity(asteroid);
    }

    @Override
    public void removed(Projectile projectile) {
        removeEntity(projectile);
    }
}
