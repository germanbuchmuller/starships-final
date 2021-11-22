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
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import player.Player;
import utils.Random;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private final RootSetter rootSetter;
    private final GameContext gameContext;
    private CollisionsVisitor collisionsVisitor;
    private MovementVisitor movementVisitor;
    private RenderVisitor renderVisitor;
    private GameEntityAutoSpawner gameEntityAutoSpawner;
    private final PlayersController playersController;
    private PlayersStatsRenderEngine playersStatsRenderEngine;
    private final ImageLoader imageLoader;
    private final Map<KeyCode, Player> keyBindings;
    private MainTimer mainTimer;
    private KeyTracker keyTracker;
    private Pane  mainPane,gamePane, uiPane;
    private boolean gamePaused;
    private long lastActionTime;
    private long gameStartedTime;



    public GameEngine(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter) {
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;
        imageLoader=new ImageLoader();
        playersController=new PlayersController();

        keyBindings=new HashMap<>();
        gamePaused=false;
    }

    public Parent launchGame() throws IOException {
        mainPane=new Pane();
        gamePane= new Pane();
        Image backImage=imageLoader.loadFromResources("background.png", gamePane.getMaxWidth(), gamePane.getMaxHeight());
        BackgroundImage background=new BackgroundImage(backImage,null, null, null, null);
        Background background1=new Background(background);
        gamePane.setBackground(background1);

        movementVisitor=new MovementVisitor(gamePane);
        renderVisitor=new RenderVisitor(gamePane);
        collisionsVisitor=new CollisionsVisitor(playersController);
        gameEntityAutoSpawner=new GameEntityAutoSpawner(gamePane);
        gameEntityAutoSpawner.addVisitor(movementVisitor);
        gameEntityAutoSpawner.addVisitor(collisionsVisitor);
        gameEntityAutoSpawner.addVisitor(renderVisitor);
        GameConfig.loadConfig("gameconfig.txt");


        Map<KeyCode,Movement> playerBindings=new HashMap<>();
        playerBindings.put(KeyCode.W,Movement.FORWARD);
        playerBindings.put(KeyCode.S,Movement.BACKWARDS);
        playerBindings.put(KeyCode.A,Movement.LEFT);
        playerBindings.put(KeyCode.D,Movement.RIGHT);
        playerBindings.put(KeyCode.Q,Movement.ROTATE_LEFT);
        playerBindings.put(KeyCode.E,Movement.ROTATE_RIGHT);
        playerBindings.put(KeyCode.SPACE,Movement.SHOOT);
        addPlayer("German",3,0,"starship.png",playerBindings);
        Map<KeyCode,Movement> playerBindings2=new HashMap<>();
        playerBindings2.put(KeyCode.NUMPAD5,Movement.FORWARD);
        playerBindings2.put(KeyCode.NUMPAD2,Movement.BACKWARDS);
        playerBindings2.put(KeyCode.NUMPAD1,Movement.LEFT);
        playerBindings2.put(KeyCode.NUMPAD3,Movement.RIGHT);
        playerBindings2.put(KeyCode.NUMPAD4,Movement.ROTATE_LEFT);
        playerBindings2.put(KeyCode.NUMPAD6,Movement.ROTATE_RIGHT);
        playerBindings2.put(KeyCode.NUMPAD0,Movement.SHOOT);
        addPlayer("Juan",3,20,"starship.png",playerBindings2);
        keyTracker=gameContext.getKeyTracker();
        playersStatsRenderEngine=new PlayersStatsRenderEngine(playersController, gamePane);
        if (mainTimer==null){
            mainTimer= new MainTimer(this);
            mainTimer.start();
        }
        gameStartedTime=System.currentTimeMillis();
        return gamePane;
    }

    public Parent start() throws IOException {

        return launchGame();
    }


    private void addPlayer(String name, int lives, int points, String shipSkin, Map<KeyCode, Movement> keyBindings ){
        Player player=new Player(name,playersController.getNewPlayerId(),lives,points,shipSkin,keyBindings);
        for (KeyCode keyCode : keyBindings.keySet()) {
            this.keyBindings.put(keyCode,player);
        }
        player.addVisitor(movementVisitor);
        player.addVisitor(collisionsVisitor);
        player.addVisitor(renderVisitor);
        playersController.addPlayer(player);
    }

    public void update(double secondsSinceLastFrame) throws IOException {
        for (KeyCode keyCode : keyTracker.getKeySet()) {
            if (keyCode==KeyCode.P && System.currentTimeMillis()-lastActionTime>700){
                gamePaused=!gamePaused;
                lastActionTime=System.currentTimeMillis();
            }
            if (!gamePaused){
                if (keyBindings.containsKey(keyCode)){
                    Player player = keyBindings.get(keyCode);
                    Map<KeyCode,Movement> playerBindings = player.getKeyBindings();
                    movementVisitor.updateUserMovableEntity(player.getShip(),playerBindings.get(keyCode),secondsSinceLastFrame);
                }
            }
        }
        if (!gamePaused){
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
        for (Player player : playersController.getPlayers()) {
            if (player.getShip().isDestroyed()){
                if (player.revive()){
                    player.getShip().revive(Random.get(20,(int)gamePane.getLayoutBounds().getMaxX()-50),Random.get(20,(int)gamePane.getLayoutBounds().getMaxY()-50));
                    player.addVisitor(movementVisitor);
                    player.addVisitor(collisionsVisitor);
                    player.addVisitor(renderVisitor);
                }
            }
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
                gameEngine.update(secondsSinceLastFrame);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




