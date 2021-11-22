package engine;

import controller.Movement;
import controller.PlayersController;
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
import org.jetbrains.annotations.NotNull;
import misc.Player;
import misc.utils.Random;

import java.io.IOException;
import java.util.*;

public class GameEngine {
    private final RootSetter rootSetter;
    private final GameContext gameContext;
    private CollisionsVisitor collisionsVisitor;
    private MovementVisitor movementVisitor;
    private RenderVisitor renderVisitor;
    private GameEntityAutoSpawner gameEntityAutoSpawner;
    private PlayersController playersController;
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

    public GameEngine(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter) throws IOException {
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;

        imageLoader=new ImageLoader();
        GameConfig.loadConfig("gameconfig.txt");

    }

    public Parent loadMainMenu() throws IOException {
        return new MainMenu(this).load();
    }

    public Parent loadNewGameMenu() throws IOException {
        return new NewGameMenu(this).load();
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

    public Parent start() throws IOException {
        return loadMainMenu();
    }

    public RootSetter getRootSetter(){
        return rootSetter;
    }

    public void initializeNewGame(){
        gamePane= new Pane();
        movementVisitor=new MovementVisitor(gamePane);
        renderVisitor=new RenderVisitor(gamePane);
        playersController=new PlayersController();
        collisionsVisitor=new CollisionsVisitor(playersController);
        keyBindings=new HashMap<>();
        gamePaused=false;
        gameEntityAutoSpawner=new GameEntityAutoSpawner(gamePane);
        gameEntityAutoSpawner.addVisitor(movementVisitor);
        gameEntityAutoSpawner.addVisitor(collisionsVisitor);
        gameEntityAutoSpawner.addVisitor(renderVisitor);
        playersStatsRenderEngine=new PlayersStatsRenderEngine(playersController, gamePane);
        keyTracker=gameContext.getKeyTracker();
        gamePaused=false;
        gameOver=false;
    }

    public void addNewPlayer(String name, String shipSkin, BulletType bulletType, Map<KeyCode, Movement> keyBindings ){
        Player player=new Player(name,playersController.getNewPlayerId(),GameConfig.PLAYER_LIVES,0,shipSkin,keyBindings);
        for (KeyCode keyCode : keyBindings.keySet()) {
            this.keyBindings.put(keyCode,player);
        }
        player.getShip().setBulletType(bulletType);
        player.addVisitor(movementVisitor);
        player.addVisitor(collisionsVisitor);
        player.addVisitor(renderVisitor);
        playersController.addPlayer(player);
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
                        gamePane.getChildren().remove(pausedGameImageView);
                    }
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
        for (Player player : playersController.getPlayers()) {
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
        if (gameOverPlayers==playersController.getPlayers().size()){
            gameOver=true;
            lastActionTime=System.currentTimeMillis();
            playersStatsRenderEngine.update();
            gamePane.getChildren().add(gamePane.getChildren().size()-(playersController.getPlayers().size()*3),gameOverImageView);
        }
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