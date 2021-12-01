package core;

import controller.Movement;
import controller.collision.CollisionChecker;
import controller.collision.CollisionsEngine;
import controller.collision.concrete.MyCollisionChecker;
import controller.collision.concrete.MyCollisionsEngine;
import controller.concrete.MyMovementEngine;
import controller.visitor.*;
import controller.visitor.concrete.MyGameState;
import core.menu.GameMenu;
import core.menu.LoadGameEvent;
import core.menu.StartNewGameEvent;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.GameContext;
import edu.austral.dissis.starships.game.GameTimer;
import edu.austral.dissis.starships.game.KeyTracker;
import edu.austral.dissis.starships.game.RootSetter;
import core.concrete.MyEntitySpawnEngine;
import core.concrete.MyGameConfig;
import core.concrete.MyGameSaver;
import core.menu.MainMenu;
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
import misc.concrete.MyWeapon;
import misc.utils.MyRandomGenerator;
import model.concrete.Ship;
import model.factory.*;
import model.factory.concrete.MyAsteroidFactory;
import model.factory.concrete.MyProjectileFactory;
import model.factory.concrete.MyShipFactory;
import org.jetbrains.annotations.NotNull;
import view.EntityImageRepository;
import view.GameWindow;
import view.PlayerStatsRenderEngine;
import view.RenderEngine;
import view.concrete.MyEntityImageRepository;
import view.concrete.MyGameWindow;
import view.concrete.MyPlayersStatsRenderEngine;
import view.concrete.MyRenderEngine;

import java.io.*;
import java.util.*;

public class GameMain {
    private final double sf=0.75;
    private final RootSetter rootSetter;
    private final GameContext gameContext;
    private GameState gameState;
    private GameWindow gameWindow;
    private final GameConfig gameConfig;
    private final GameSaver gameSaver;
    private final GameMenu homeMenu;

    private RenderEngine renderEngine;
    private PlayerStatsRenderEngine playerStatsRenderEngine;
    private MyMovementEngine movementEngine;
    private CollisionsEngine collisionsEngine;

    private PlayersRepository playersRepository;
    private PointsRepository pointsRepository;
    private EntityImageRepository entityImageRepository;

    private ShipFactory shipFactory;

    private EntitySpawnEngine entitySpawnEngine;


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



    public GameMain(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter){
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;
        imageLoader=new ImageLoader();
        gameConfig=new MyGameConfig("gameconfig.txt");
        gameSaver=new MyGameSaver(gameConfig, new MyProjectileFactory(gameConfig));
        homeMenu=new MainMenu( new StartNewGameEvent(this), new LoadGameEvent(this));
    }

    public Parent start() throws IOException {
        return loadMainMenu();
    }

    public Parent loadMainMenu() throws IOException {
        return homeMenu.load();
    }

    public void startNewGame() throws IOException {
        initializeNewGame();
        rootSetter.setRoot(launchGame());
        startNewGameFromConfigFile();
    }

    public void loadGame() throws Exception {
        initializeSavedGame();
        rootSetter.setRoot(launchGame());
        startGameFromSaveGame();
    }

    private void startNewGameFromConfigFile(){
        for (int i = 0; i < gameConfig.getPlayersBindingsList().size(); i++) {
            addNewPlayer("Player "+(i+1), gameConfig.getPlayerSkinList().get(i), gameConfig.getPlayerBulletTypeList().get(i), gameConfig.getPlayersBindingsList().get(i));
        }
    }

    private void startGameFromSaveGame(){
        for (Player player : gameState.getPlayers()) {
            loadPlayerToGame(player);
        }
        playersRepository.addPlayers(gameState.getPlayers());
    }

    private void initializeNewGame(){
        gameState=new MyGameState();
        initializeGame();
    }

    private void initializeSavedGame() throws Exception {
        gameState=gameSaver.initialize();
        initializeGame();
        gameSaver.loadGame();
    }

    private void initializeGame(){
        gameWindow=new MyGameWindow();
        gamePane=gameWindow.init();
        entityImageRepository=new MyEntityImageRepository(imageLoader);
        playersRepository =new MyPlayersRepository(gameState);
        pointsRepository=new MyPointsRepository();

        renderEngine=new MyRenderEngine(gameState,gameWindow,entityImageRepository);
        movementEngine=new MyMovementEngine(gameState);
        collisionsEngine=new MyCollisionsEngine(gameState,playersRepository,pointsRepository);
        CollisionChecker collisionChecker=new MyCollisionChecker(collisionsEngine.getColliders());
        shipFactory=new MyShipFactory(gameWindow,collisionChecker,new MyRandomGenerator());
        entitySpawnEngine=new MyEntitySpawnEngine(gameState,gameConfig,new MyAsteroidFactory(collisionChecker,gameWindow, new MyRandomGenerator()), new MyRandomGenerator());

        keyBindings=new HashMap<>();
        playerStatsRenderEngine =new MyPlayersStatsRenderEngine(playersRepository, gameWindow);
        keyTracker=gameContext.getKeyTracker();
        gamePaused=false;
        gameOver=false;
    }

    private Parent launchGame() throws IOException {
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

    private void addNewPlayer(String name, String shipSkin, BulletType bulletType, Map<KeyCode, Movement> keyBindings ){
        int playerId=playersRepository.getNewPlayerId();
        Weapon weapon=new MyWeapon(gameConfig.getBulletCoolDown(),new MyProjectileFactory(gameConfig),playerId);
        shipFactory.setWeapon(weapon);
        shipFactory.setImageFileName(shipSkin);
        shipFactory.setPlayerId(playerId);
        shipFactory.setHealth(gameConfig.getShipMaxHealth());
        shipFactory.setMaxHealth(gameConfig.getShipMaxHealth());
        shipFactory.setAcceleration(gameConfig.getShipAcceleration());
        shipFactory.setMaxSpeed(gameConfig.getShipMaxSpeed());
        shipFactory.setWidth(gameConfig.getShipWidth());
        shipFactory.setHeight(gameConfig.getShipHeight());
        shipFactory.setRandomSpawn(true);
        Ship ship = shipFactory.getEntity();
        ship.setBulletType(bulletType);
        Player player=new Player(name,gameConfig.getPlayerLives(),0,ship,keyBindings);
        loadPlayerToGameFromConfigFile(player);
    }

    private void loadPlayerToGameFromConfigFile(Player player){
        loadPlayerToGame(player);
        playersRepository.addPlayer(player);
    }

    private void loadPlayerToGame(Player player){
        for (KeyCode keyCode : player.getKeyBindings().keySet()) {
            this.keyBindings.put(keyCode,player);
        }
        if (player.getLives()==0)player.getShip().harm(99999);
        player.addVisitor(gameState);
    }

    public void nextFrame(double secondsSinceLastFrame) throws Exception {
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
                    gameSaver.saveGame(gameState);
                    gameSaved=true;
                }
                if (!gamePaused){
                    if (keyBindings.containsKey(keyCode)){
                        Player player = keyBindings.get(keyCode);
                        movementEngine.updateUserMovableEntity(player.getShip(),player.getKeyBindings().get(keyCode),secondsSinceLastFrame);
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
            playerStatsRenderEngine.update();
            checkDeadPlayers();
        }
    }

    private void checkDeadPlayers(){
        int gameOverPlayers = 0;
        for (Player player : playersRepository.getPlayers()) {
            if (!player.getShip().isAlive()){
                if (player.revive()){
                    shipFactory.reviveShip(player.getShip());
                }else{
                    gameOverPlayers++;
                    gameState.reject(player.getShip());
                }
            }
        }
        if (gameOverPlayers== playersRepository.getPlayers().size()){
            gameOver=true;
            lastActionTime=System.currentTimeMillis();
            playerStatsRenderEngine.update();
            gamePane.getChildren().add(gamePane.getChildren().size()-playerStatsRenderEngine.getViewCount(),gameOverImageView);
        }
    }

    private static class MainTimer extends GameTimer{
        private final GameMain gameMain;
        public MainTimer(GameMain gameMain) {
            this.gameMain = gameMain;
        }

        @Override
        public void nextFrame(double secondsSinceLastFrame) {
            try {
                gameMain.nextFrame(secondsSinceLastFrame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}