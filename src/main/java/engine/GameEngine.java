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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import misc.Player;
import misc.utils.Random;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    private Pane  gamePane;
    private boolean gamePaused;
    private long lastActionTime;
    private long gameStartedTime;
    private ImageView pausedGameImageView;



    public GameEngine(@NotNull GameContext gameContext, @NotNull RootSetter rootSetter) throws IOException {
        this.rootSetter=rootSetter;
        this.gameContext=gameContext;
        imageLoader=new ImageLoader();
        GameConfig.loadConfig("gameconfig.txt");
    }

    public Parent loadMainMenu() throws IOException {
        Pane pane = new Pane();

        Image img = imageLoader.loadFromResources("menu-background.png", 1920, 1080);
        ImageView imageView = new ImageView(img);

        Image newGameImage1 = imageLoader.loadFromResources("newGameBtn1.png", 620, 157);
        Image newGameImage2 = imageLoader.loadFromResources("newGameBtn2.png", 620, 157);
        Image newGameImage3 = imageLoader.loadFromResources("newGameBtn3.png", 620, 157);
        ImageView newGameBtn = new ImageView(newGameImage1);
        newGameBtn.setLayoutX(660);
        newGameBtn.setLayoutY(530);
        newGameBtn.setOnMouseEntered(event -> newGameBtn.setImage(newGameImage2));
        newGameBtn.setOnMouseExited(event -> newGameBtn.setImage(newGameImage1));
        newGameBtn.setOnMousePressed(event -> newGameBtn.setImage(newGameImage3));
        newGameBtn.setOnMouseReleased(event -> newGameBtn.setImage(newGameImage2));
        newGameBtn.setOnMouseClicked(event -> {
            try {
                rootSetter.setRoot(loadNewGameMenu());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Image loadGameImage1 = imageLoader.loadFromResources("loadGameBtn1.png", 620, 157);
        Image loadGameImage2 = imageLoader.loadFromResources("loadGameBtn2.png", 620, 157);
        Image loadGameImage3 = imageLoader.loadFromResources("loadGameBtn3.png", 620, 157);
        ImageView loadGameBtn = new ImageView(loadGameImage1);
        loadGameBtn.setLayoutX(660);
        loadGameBtn.setLayoutY(700);
        loadGameBtn.setOnMouseEntered(event -> loadGameBtn.setImage(loadGameImage2));
        loadGameBtn.setOnMouseExited(event -> loadGameBtn.setImage(loadGameImage1));
        loadGameBtn.setOnMousePressed(event -> loadGameBtn.setImage(loadGameImage3));
        loadGameBtn.setOnMouseReleased(event -> loadGameBtn.setImage(loadGameImage2));

        Image quitGameImage1 = imageLoader.loadFromResources("quitGameBtn1.png", 620, 157);
        Image quitGameImage2 = imageLoader.loadFromResources("quitGameBtn2.png", 620, 157);
        Image quitGameImage3 = imageLoader.loadFromResources("quitGameBtn3.png", 620, 157);
        ImageView quitGameBtn = new ImageView(quitGameImage1);
        quitGameBtn.setLayoutX(660);
        quitGameBtn.setLayoutY(870);
        quitGameBtn.setOnMouseEntered(event -> quitGameBtn.setImage(quitGameImage2));
        quitGameBtn.setOnMouseExited(event -> quitGameBtn.setImage(quitGameImage1));
        quitGameBtn.setOnMousePressed(event -> quitGameBtn.setImage(quitGameImage3));
        quitGameBtn.setOnMouseReleased(event -> quitGameBtn.setImage(quitGameImage2));
        quitGameBtn.setOnMouseClicked(event -> System.exit(0));

        pane.getChildren().add(imageView);
        pane.getChildren().add(newGameBtn);
        pane.getChildren().add(loadGameBtn);
        pane.getChildren().add(quitGameBtn);
        return pane;
    }

    public Parent loadNewGameMenu() throws IOException {
        Pane pane = new Pane();

        Image img = imageLoader.loadFromResources("newgamemenu.png", 1920, 1080);
        ImageView background = new ImageView(img);

        AtomicReference<String> player1ShipTexture = new AtomicReference<>(GameConfig.SHIP_TEXTURE.get(0));
        AtomicInteger player1ShipTextureIndex= new AtomicInteger();
        Image player1ShipImg = imageLoader.loadFromResources(player1ShipTexture.get(), 229, 229);
        ImageView player1ShipImageView = new ImageView(player1ShipImg);
        player1ShipImageView.setLayoutX(640);
        player1ShipImageView.setLayoutY(334);



        Image changeShipBtnImg = imageLoader.loadFromResources("changeshipbtn.png", 362, 66);
        ImageView changePlayer1ShipBtn = new ImageView(changeShipBtnImg);
        changePlayer1ShipBtn.setLayoutX(123);
        changePlayer1ShipBtn.setLayoutY(952);
        changePlayer1ShipBtn.setOnMouseClicked(event -> {
            if (GameConfig.SHIP_TEXTURE.size()-1>player1ShipTextureIndex.get()){
                player1ShipTextureIndex.addAndGet(1);

            }else{
                player1ShipTextureIndex.set(0);
            }
            player1ShipTexture.set(GameConfig.SHIP_TEXTURE.get(player1ShipTextureIndex.get()));
            Image player1NewShipImg = null;
            try {
                player1NewShipImg = imageLoader.loadFromResources(player1ShipTexture.get(), 229, 229);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player1ShipImageView.setImage(player1NewShipImg);
        });



        pane.getChildren().add(background);
        pane.getChildren().add(player1ShipImageView);
        pane.getChildren().add(changePlayer1ShipBtn);
        return pane;
    }

    public Parent launchGame() throws IOException {
        gamePane= new Pane();
        Image backImage=imageLoader.loadFromResources("background.png", gamePane.getMaxWidth(), gamePane.getMaxHeight());
        BackgroundImage background=new BackgroundImage(backImage,null, null, null, null);
        Background background1=new Background(background);
        gamePane.setBackground(background1);

        Image img = imageLoader.loadFromResources("paused-image.png", 1920, 1080);
        pausedGameImageView = new ImageView(img);
        playersController=new PlayersController();
        keyBindings=new HashMap<>();
        gamePaused=false;

        movementVisitor=new MovementVisitor(gamePane);
        renderVisitor=new RenderVisitor(gamePane);
        collisionsVisitor=new CollisionsVisitor(playersController);
        gameEntityAutoSpawner=new GameEntityAutoSpawner(gamePane);
        gameEntityAutoSpawner.addVisitor(movementVisitor);
        gameEntityAutoSpawner.addVisitor(collisionsVisitor);
        gameEntityAutoSpawner.addVisitor(renderVisitor);



        Map<KeyCode,Movement> playerBindings=new HashMap<>();
        playerBindings.put(KeyCode.W,Movement.FORWARD);
        playerBindings.put(KeyCode.S,Movement.BACKWARDS);
        playerBindings.put(KeyCode.A,Movement.LEFT);
        playerBindings.put(KeyCode.D,Movement.RIGHT);
        playerBindings.put(KeyCode.Q,Movement.ROTATE_LEFT);
        playerBindings.put(KeyCode.E,Movement.ROTATE_RIGHT);
        playerBindings.put(KeyCode.SPACE,Movement.SHOOT);
        addPlayer("German",GameConfig.PLAYER_LIVES,0,"starship.png",playerBindings);
        Map<KeyCode,Movement> playerBindings2=new HashMap<>();
        playerBindings2.put(KeyCode.NUMPAD5,Movement.FORWARD);
        playerBindings2.put(KeyCode.NUMPAD2,Movement.BACKWARDS);
        playerBindings2.put(KeyCode.NUMPAD1,Movement.LEFT);
        playerBindings2.put(KeyCode.NUMPAD3,Movement.RIGHT);
        playerBindings2.put(KeyCode.NUMPAD4,Movement.ROTATE_LEFT);
        playerBindings2.put(KeyCode.NUMPAD6,Movement.ROTATE_RIGHT);
        playerBindings2.put(KeyCode.NUMPAD0,Movement.SHOOT);
        addPlayer("Juan",GameConfig.PLAYER_LIVES,0,"starship.png",playerBindings2);
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

        return loadMainMenu();
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