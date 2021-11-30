package engine;

import controller.Movement;
import controller.collision.CollisionsEngine;
import controller.collision.concrete.MyCollisionChecker;
import controller.collision.concrete.MyCollisionsEngine;
import controller.concrete.MyMovementEngine;
import controller.visitor.*;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.GameTimer;
import edu.austral.dissis.starships.game.KeyTracker;
import edu.austral.dissis.starships.game.RootSetter;
import engine.concrete.MyEntitySpawnEngine;
import engine.concrete.MyGameConfig;
import engine.menu.MainMenu;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import misc.*;
import misc.concrete.MyPlayersRepository;
import misc.concrete.MyPointsRepository;
import model.Entity;
import model.factory.EntityFactory;
import model.factory.MyEntityFactory;
import org.jetbrains.annotations.NotNull;
import misc.utils.Random;
import view.EntityImageRepository;
import view.GameWindow;
import view.RenderEngine;
import view.concrete.MyEntityImageRepository;
import view.concrete.MyGameWindow;
import view.concrete.MyRenderEngine;

import java.io.*;
import java.util.*;

public class GameCore {
    private final double sf=0.75;
    private final RootSetter rootSetter;
    private final GameContext gameContext;
    private GameState gameState;
    private GameWindow gameWindow;
    private GameConfig gameConfig;
    private RenderEngine renderEngine;
    private MyMovementEngine movementEngine;
    private CollisionsEngine collisionsEngine;

    //private GameEntityAutoSpawner gameEntityAutoSpawner;
    private PlayersRepository playersRepository;
    private PointsRepository pointsRepository;
    private EntityImageRepository entityImageRepository;

    private EntityFactory entityFactory;
    private EntitySpawnEngine entitySpawnEngine;

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



    public GameCore(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter) throws IOException {
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;
        imageLoader=new ImageLoader();
        MyGameConfig.loadConfig("gameconfig.txt");
    }

    public Parent start() throws IOException {
        return loadMainMenu();
    }

    public Parent loadMainMenu() throws IOException {
        return new MainMenu(this).load();
    }

    public void startNewGameFromConfigFile(){
        initializeNewGame();
        for (int i = 0; i < MyGameConfig.playerBindingsList.size(); i++) {
            addNewPlayer("Player"+(i+1), MyGameConfig.playersSkin.get(i), MyGameConfig.playersBulletType.get(i), MyGameConfig.playerBindingsList.get(i));
        }
    }

    public void initializeNewGame(){
        gameState=new MyGameState();
        gameWindow=new MyGameWindow();
        gamePane=gameWindow.init();
        gameConfig=new MyGameConfig("gameconfig.txt");

        entityImageRepository=new MyEntityImageRepository(imageLoader);
        renderEngine=new MyRenderEngine(gameState,gameWindow,entityImageRepository);
        movementEngine=new MyMovementEngine(gameState);
        playersRepository =new MyPlayersRepository();
        pointsRepository=new MyPointsRepository();
        collisionsEngine=new MyCollisionsEngine(gameState,playersRepository,pointsRepository);
        entityFactory=new MyEntityFactory(gameConfig,collisionsEngine.getColliders(),new MyCollisionChecker(),gameWindow);
        entitySpawnEngine=new MyEntitySpawnEngine(gameState,gameConfig, gameWindow,collisionsEngine.getColliders(),new MyCollisionChecker());

        keyBindings=new HashMap<>();
        playersStatsRenderEngine=new PlayersStatsRenderEngine(playersRepository, gamePane);
        keyTracker=gameContext.getKeyTracker();
        gamePaused=false;
        gameOver=false;
    }

    public Parent launchGame() throws IOException {
        Image backImage=imageLoader.loadFromResources("background.png", gamePane.getMaxWidth(), gamePane.getMaxHeight());
        BackgroundImage background=new BackgroundImage(backImage,null, null, null, null);
        Background background1=new Background(background);
        gamePane.setBackground(background1);

        Image pausedImg = imageLoader.loadFromResources("paused-image.png", 1920*sf, 1080*sf);
        Image gameOverImg = imageLoader.loadFromResources("gameOver.png", 1920*sf, 1080*sf);
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
        Player player=new Player(name, playersRepository.getNewPlayerId(), MyGameConfig.PLAYER_LIVES,0,shipSkin,keyBindings);
        player.getShip().setBulletType(bulletType);
        loadPlayerToGame(player);
    }

    private void loadPlayerToGame(Player player){
        for (KeyCode keyCode : player.getKeyBindings().keySet()) {
            this.keyBindings.put(keyCode,player);
        }
        if (player.getLives()==0)player.getShip().destroy();
        player.addVisitor(gameState);
        playersRepository.addPlayer(player);
    }

    private void loadEntityToGame(Entity entity){
        //entity.accept(collisionsVisitor);
        entity.accept(gameState);
        //entity.accept(movementVisitor);
        //entity.accept(renderVisitor);
        //gameEntityAutoSpawner.addEntity(entity);
    }
/*
    public void saveGame() {

        try {
            FileOutputStream playersFOS = new FileOutputStream("players.savegame");
            ObjectOutputStream playersOOS = new ObjectOutputStream(playersFOS);
            for (Player player : playersRepository.getPlayers()) {
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
*/
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
                    //saveGame();
                    gameSaved=true;
                }
                if (!gamePaused){
                    if (keyBindings.containsKey(keyCode)){
                        Player player = keyBindings.get(keyCode);
                        Map<KeyCode,Movement> playerBindings = player.getKeyBindings();
                        movementEngine.updateUserMovableEntity(player.getShip(),playerBindings.get(keyCode),secondsSinceLastFrame);
                    }
                }
            }else if (System.currentTimeMillis()-lastActionTime>1000){
                rootSetter.setRoot(loadMainMenu());
            }
        }
        if (!gamePaused && !gameOver){
            movementEngine.update(secondsSinceLastFrame);
            collisionsEngine.update();
            renderEngine.update();
            if (System.currentTimeMillis()-gameStartedTime>2000){
                entitySpawnEngine.update();
            }
            playersStatsRenderEngine.update();
            checkDeadPlayers();
        }
    }

    private void checkDeadPlayers(){
        int gameOverPlayers = 0;
        for (Player player : playersRepository.getPlayers()) {
            if (!player.getShip().isAlive()){
                if (player.revive()){
                    player.getShip().revive(Random.get(20,(int)gamePane.getLayoutBounds().getMaxX()-50),Random.get(20,(int)gamePane.getLayoutBounds().getMaxY()-50));
                }else{
                    gameOverPlayers++;
                }
            }
        }
        if (gameOverPlayers== playersRepository.getPlayers().size()){
            gameOver=true;
            lastActionTime=System.currentTimeMillis();
            playersStatsRenderEngine.update();
            gamePane.getChildren().add(gamePane.getChildren().size()-(playersRepository.getPlayers().size()*3),gameOverImageView);
        }
    }

    public RootSetter getRootSetter(){
        return rootSetter;
    }

    private static class MainTimer extends GameTimer{
        private final GameCore gameCore;
        public MainTimer(GameCore gameCore) {
            this.gameCore = gameCore;
        }

        @Override
        public void nextFrame(double secondsSinceLastFrame) {
            try {
                gameCore.nextFrame(secondsSinceLastFrame);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}