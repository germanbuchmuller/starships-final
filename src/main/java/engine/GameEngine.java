package engine;

import controller.Movement;
import controller.visitor.CollisionsVisitor;
import controller.visitor.MovementVisitor;
import controller.visitor.RenderVisitor;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.GameTimer;
import edu.austral.dissis.starships.game.KeyTracker;
import edu.austral.dissis.starships.game.RootSetter;
import engine.menu.MainMenu;
import engine.menu.NewGameMenu;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import misc.BulletType;
import model.Entity;
import org.jetbrains.annotations.NotNull;
import misc.Player;
import misc.utils.Random;
import serialize.SerializedEntity;
import serialize.SerializedPlayer;

import java.io.*;
import java.util.*;

public class GameEngine {
    private final RootSetter rootSetter;
    private final GameContext gameContext;
    private CollisionsVisitor collisionsVisitor;
    private MovementVisitor movementVisitor;
    private RenderVisitor renderVisitor;
    private GameEntityAutoSpawner gameEntityAutoSpawner;
    private PlayersManager playersManager;
    private PlayersStatsRenderEngine playersStatsRenderEngine;
    private final ImageLoader imageLoader;
    private Map<KeyCode, Player> keyBindings;
    private MainTimer mainTimer;
    private KeyTracker keyTracker;
    private Pane gamePane;
    private boolean gamePaused, gameOver;
    private long lastActionTime;
    private long gameStartedTime;
    private ImageView pausedGameImageView;
    private ImageView gameOverImageView;
    private boolean gameSaved;

    public GameEngine(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter) throws IOException {
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;
        imageLoader=new ImageLoader();
        GameConfig.loadConfig("gameconfig.txt");
    }

    public Parent start() throws IOException {
        return loadMainMenu();
    }

    public Parent loadMainMenu() throws IOException {
        return new MainMenu(this).load();
    }

    public Parent loadNewGameMenu() throws IOException {
        return new NewGameMenu(this).load();
    }

    public void initializeNewGame(){
        gamePane= new Pane();
        movementVisitor=new MovementVisitor(gamePane);
        renderVisitor=new RenderVisitor(gamePane);
        playersManager =new PlayersManager();
        collisionsVisitor=new CollisionsVisitor(playersManager);
        keyBindings=new HashMap<>();
        gamePaused=false;
        gameEntityAutoSpawner=new GameEntityAutoSpawner(gamePane);
        gameEntityAutoSpawner.addVisitor(movementVisitor);
        gameEntityAutoSpawner.addVisitor(collisionsVisitor);
        gameEntityAutoSpawner.addVisitor(renderVisitor);
        playersStatsRenderEngine=new PlayersStatsRenderEngine(playersManager, gamePane);
        keyTracker=gameContext.getKeyTracker();
        gamePaused=false;
        gameOver=false;
    }

    public Parent launchGame() throws IOException {
        Image backImage=imageLoader.loadFromResources("background.png", gamePane.getMaxWidth(), gamePane.getMaxHeight());
        BackgroundImage background=new BackgroundImage(backImage,null, null, null, null);
        Background background1=new Background(background);
        gamePane.setBackground(background1);

        Image pausedImg = imageLoader.loadFromResources("paused-image.png", 1920, 1080);
        Image gameOverImg = imageLoader.loadFromResources("gameOver.png", 1920, 1080);
        pausedGameImageView = new ImageView(pausedImg);
        gameOverImageView = new ImageView(gameOverImg);

        if (mainTimer==null){
            mainTimer= new MainTimer(this);
            mainTimer.start();
        }
        gameStartedTime=System.currentTimeMillis();
        return gamePane;
    }

    public void addNewPlayer(String name, String shipSkin, BulletType bulletType, Map<KeyCode, Movement> keyBindings ){
        Player player=new Player(name, playersManager.getNewPlayerId(),GameConfig.PLAYER_LIVES,0,shipSkin,keyBindings);
        player.getShip().setBulletType(bulletType);
        loadPlayerToGame(player);
    }

    private void loadPlayerToGame(Player player){
        for (KeyCode keyCode : player.getKeyBindings().keySet()) {
            this.keyBindings.put(keyCode,player);
        }
        if (player.getLives()==0)player.getShip().destroy();
        player.addVisitor(movementVisitor);
        player.addVisitor(collisionsVisitor);
        player.addVisitor(renderVisitor);
        playersManager.addPlayer(player);
    }

    private void loadEntityToGame(Entity entity){
        entity.accept(collisionsVisitor);
        entity.accept(movementVisitor);
        entity.accept(renderVisitor);
        gameEntityAutoSpawner.addEntity(entity);
    }

    public void saveGame() {
        try {
            FileOutputStream playersFOS = new FileOutputStream("players.savegame");
            ObjectOutputStream playersOOS = new ObjectOutputStream(playersFOS);
            for (Player player : playersManager.getPlayers()) {
                playersOOS.writeObject(player.toSerializablePlayer());
            }
            playersOOS.close();

            FileOutputStream entitiesFOS = new FileOutputStream("entities.savegame");
            ObjectOutputStream entitiesOOS = new ObjectOutputStream(entitiesFOS);
            for (Entity selfMovableEntity : movementVisitor.getSelfMovableEntities()) {
                entitiesOOS.writeObject(selfMovableEntity.toSerializedEntity());
            }
            entitiesOOS.close();
            System.out.println("Game saved with success");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadSavedGame(){
        initializeNewGame();
        try{
            FileInputStream playersFIS = new FileInputStream("players.savegame");
            ObjectInputStream playersOIS = new ObjectInputStream(playersFIS);

            SerializedPlayer serializedPlayer;
            while ((serializedPlayer = (SerializedPlayer)playersOIS.readObject())!=null){
                Player player=serializedPlayer.toPlayer();
                loadPlayerToGame(player);
            }
            playersOIS.close();
            System.out.println("ola");
        }catch (Exception ignored){
            try{
                loadNewGameMenu();
            }catch (Exception ignored2){}
        }
        try{
            FileInputStream entityFIS = new FileInputStream("entities.savegame");
            ObjectInputStream entitiesOIS = new ObjectInputStream(entityFIS);
            SerializedEntity serializedEntity;
            while ((serializedEntity =(SerializedEntity) entitiesOIS.readObject())!=null){
                Entity entity = serializedEntity.toEntity();
                loadEntityToGame(entity);
                System.out.println(entity);
            }
            entitiesOIS.close();
        }catch (Exception ignore){}
    }

    public void nextFrame(double secondsSinceLastFrame) throws IOException {
        for (KeyCode keyCode : keyTracker.getKeySet()) {
            if (!gameOver){
                if (keyCode==KeyCode.P && System.currentTimeMillis()-lastActionTime>300){
                    gamePaused=!gamePaused;
                    lastActionTime=System.currentTimeMillis();
                    if (gamePaused){
                        gamePane.getChildren().add(pausedGameImageView);
                    }else{
                        gameSaved=false;
                        gamePane.getChildren().remove(pausedGameImageView);
                    }
                }else if(gamePaused && keyCode==KeyCode.S && System.currentTimeMillis()-lastActionTime>300 && !gameSaved){
                    lastActionTime=System.currentTimeMillis();
                    saveGame();
                    gameSaved=true;
                }
                if (!gamePaused){
                    if (keyBindings.containsKey(keyCode)){
                        Player player = keyBindings.get(keyCode);
                        Map<KeyCode,Movement> playerBindings = player.getKeyBindings();
                        movementVisitor.updateUserMovableEntity(player.getShip(),playerBindings.get(keyCode),secondsSinceLastFrame);
                    }
                }
            }else if (System.currentTimeMillis()-lastActionTime>1000){
                rootSetter.setRoot(loadMainMenu());
            }
        }
        if (!gamePaused && !gameOver){
            movementVisitor.updateSelfMovableEntities(secondsSinceLastFrame);
            collisionsVisitor.checkColisions();
            renderVisitor.update();
            if (System.currentTimeMillis()-gameStartedTime>2000){
                gameEntityAutoSpawner.update();
            }
            playersStatsRenderEngine.update();
            checkDeadPlayers();
        }
    }

    private void checkDeadPlayers(){
        int gameOverPlayers = 0;
        for (Player player : playersManager.getPlayers()) {
            if (player.getShip().isDestroyed()){
                if (player.revive()){
                    player.getShip().revive(Random.get(20,(int)gamePane.getLayoutBounds().getMaxX()-50),Random.get(20,(int)gamePane.getLayoutBounds().getMaxY()-50));
                    player.addVisitor(movementVisitor);
                    player.addVisitor(collisionsVisitor);
                    player.addVisitor(renderVisitor);
                }else{
                    gameOverPlayers++;
                }
            }
        }
        if (gameOverPlayers== playersManager.getPlayers().size()){
            gameOver=true;
            lastActionTime=System.currentTimeMillis();
            playersStatsRenderEngine.update();
            gamePane.getChildren().add(gamePane.getChildren().size()-(playersManager.getPlayers().size()*3),gameOverImageView);
        }
    }

    public RootSetter getRootSetter(){
        return rootSetter;
    }

    private static class MainTimer extends GameTimer{
        private final GameEngine gameEngine;
        public MainTimer(GameEngine gameEngine) {
            this.gameEngine=gameEngine;
        }

        @Override
        public void nextFrame(double secondsSinceLastFrame) {
            try {
                gameEngine.nextFrame(secondsSinceLastFrame);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}