package engine.menu;

import controller.Movement;
import edu.austral.dissis.starships.file.ImageLoader;
import edu.austral.dissis.starships.game.RootSetter;
import engine.GameConfig;
import engine.GameEngine;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import misc.BulletType;
import misc.Player;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NewGameMenu {
    private final ImageLoader imageLoader;
    private final GameEngine gameEngine;

    public NewGameMenu(GameEngine gameEngine) {
        imageLoader=new ImageLoader();
        this.gameEngine=gameEngine;
    }

    public Parent load() throws IOException {
        Pane pane = new Pane();

        Image img = imageLoader.loadFromResources("newgamemenu.png", 1920, 1080);
        ImageView background = new ImageView(img);

        AtomicReference<String> player1ShipTexture = new AtomicReference<>(GameConfig.SHIP_TEXTURE.get(0));
        AtomicInteger player1ShipTextureIndex= new AtomicInteger();
        Image player1ShipImg = imageLoader.loadFromResources(player1ShipTexture.get(), 229, 229);
        ImageView player1ShipImageView = new ImageView(player1ShipImg);
        player1ShipImageView.setLayoutX(640);
        player1ShipImageView.setLayoutY(334);

        Map<Movement, KeyCode> player1Bindings=new HashMap<>();
        player1Bindings.put(Movement.FORWARD,KeyCode.W);
        player1Bindings.put(Movement.BACKWARDS,KeyCode.S);
        player1Bindings.put(Movement.LEFT,KeyCode.A);
        player1Bindings.put(Movement.RIGHT,KeyCode.D);
        player1Bindings.put(Movement.ROTATE_LEFT,KeyCode.Q);
        player1Bindings.put(Movement.ROTATE_RIGHT,KeyCode.E);
        player1Bindings.put(Movement.SHOOT,KeyCode.R);

        TextField player1ForwardText = new TextField(player1Bindings.get(Movement.FORWARD).getName());
        player1ForwardText.setFont(new Font(30));
        player1ForwardText.setLayoutX(400);
        player1ForwardText.setLayoutY(385);
        player1ForwardText.setPrefWidth(70);

        TextField player1BackwardsText = new TextField(player1Bindings.get(Movement.BACKWARDS).getName());
        player1BackwardsText.setFont(new Font(30));
        player1BackwardsText.setLayoutX(471);
        player1BackwardsText.setLayoutY(456);
        player1BackwardsText.setPrefWidth(70);

        TextField player1LeftText = new TextField(player1Bindings.get(Movement.LEFT).getName());
        player1LeftText.setFont(new Font(30));
        player1LeftText.setLayoutX(320);
        player1LeftText.setLayoutY(523);
        player1LeftText.setPrefWidth(70);

        TextField player1RightText = new TextField(player1Bindings.get(Movement.RIGHT).getName());
        player1RightText.setFont(new Font(30));
        player1RightText.setLayoutX(320);
        player1RightText.setLayoutY(595);
        player1RightText.setPrefWidth(70);

        TextField player1RLeftText = new TextField(player1Bindings.get(Movement.ROTATE_LEFT).getName());
        player1RLeftText.setFont(new Font(30));
        player1RLeftText.setLayoutX(517);
        player1RLeftText.setLayoutY(660);
        player1RLeftText.setPrefWidth(70);

        TextField player1RRightText = new TextField(player1Bindings.get(Movement.ROTATE_RIGHT).getName());
        player1RRightText.setFont(new Font(30));
        player1RRightText.setLayoutX(530);
        player1RRightText.setLayoutY(735);
        player1RRightText.setPrefWidth(70);

        TextField player1ShootText = new TextField(player1Bindings.get(Movement.SHOOT).getName());
        player1ShootText.setFont(new Font(30));
        player1ShootText.setLayoutX(339);
        player1ShootText.setLayoutY(807);
        player1ShootText.setPrefWidth(70);

        List<BulletType> bulletTypes = new ArrayList<>(Arrays.asList(BulletType.values()));
        AtomicInteger player1BulletTypesIndex= new AtomicInteger();
        Text player1BulletTypeText = new Text(bulletTypes.get(0).name());
        player1BulletTypeText.setFont(new Font(50));
        player1BulletTypeText.setFill(Color.WHITE);
        player1BulletTypeText.setLayoutX(497);
        player1BulletTypeText.setLayoutY(920);
        player1BulletTypeText.setOnMouseClicked(event -> {
            if (bulletTypes.size()-1> player1BulletTypesIndex.get()){
                player1BulletTypesIndex.addAndGet(1);
            }else{
                player1BulletTypesIndex.set(0);
            }
            player1BulletTypeText.setText(String.valueOf(bulletTypes.get(player1BulletTypesIndex.get())));
        });

        Image changePlayer1ShipBtnImg = imageLoader.loadFromResources("changeshipbtn.png", 362, 66);
        ImageView changePlayer1ShipBtn = new ImageView(changePlayer1ShipBtnImg);
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

        int p2o=938;

        AtomicReference<String> player2ShipTexture = new AtomicReference<>(GameConfig.SHIP_TEXTURE.get(0));
        AtomicInteger player2ShipTextureIndex= new AtomicInteger();
        Image player2ShipImg = imageLoader.loadFromResources(player2ShipTexture.get(), 229, 229);
        ImageView player2ShipImageView = new ImageView(player2ShipImg);
        player2ShipImageView.setLayoutX(640+p2o);
        player2ShipImageView.setLayoutY(334);

        Map<Movement,KeyCode> player2Bindings=new HashMap<>();
        player2Bindings.put(Movement.FORWARD,KeyCode.I);
        player2Bindings.put(Movement.BACKWARDS,KeyCode.K);
        player2Bindings.put(Movement.LEFT,KeyCode.J);
        player2Bindings.put(Movement.RIGHT,KeyCode.L);
        player2Bindings.put(Movement.ROTATE_LEFT,KeyCode.U);
        player2Bindings.put(Movement.ROTATE_RIGHT,KeyCode.O);
        player2Bindings.put(Movement.SHOOT,KeyCode.M);

        TextField player2ForwardText = new TextField(player2Bindings.get(Movement.FORWARD).getName());
        player2ForwardText.setFont(new Font(30));
        player2ForwardText.setLayoutX(400+p2o);
        player2ForwardText.setLayoutY(385);
        player2ForwardText.setPrefWidth(70);

        TextField player2BackwardsText = new TextField(player2Bindings.get(Movement.BACKWARDS).getName());
        player2BackwardsText.setFont(new Font(30));
        player2BackwardsText.setLayoutX(471+p2o);
        player2BackwardsText.setLayoutY(456);
        player2BackwardsText.setPrefWidth(70);

        TextField player2LeftText = new TextField(player2Bindings.get(Movement.LEFT).getName());
        player2LeftText.setFont(new Font(30));
        player2LeftText.setLayoutX(320+p2o);
        player2LeftText.setLayoutY(523);
        player2LeftText.setPrefWidth(70);

        TextField player2RightText = new TextField(player2Bindings.get(Movement.RIGHT).getName());
        player2RightText.setFont(new Font(30));
        player2RightText.setLayoutX(320+p2o);
        player2RightText.setLayoutY(595);
        player2RightText.setPrefWidth(70);

        TextField player2RLeftText = new TextField(player2Bindings.get(Movement.ROTATE_LEFT).getName());
        player2RLeftText.setFont(new Font(30));
        player2RLeftText.setLayoutX(517+p2o);
        player2RLeftText.setLayoutY(660);
        player2RLeftText.setPrefWidth(70);

        TextField player2RRightText = new TextField(player2Bindings.get(Movement.ROTATE_RIGHT).getName());
        player2RRightText.setFont(new Font(30));
        player2RRightText.setLayoutX(530+p2o);
        player2RRightText.setLayoutY(735);
        player2RRightText.setPrefWidth(70);

        TextField player2ShootText = new TextField(player2Bindings.get(Movement.SHOOT).getName());
        player2ShootText.setFont(new Font(30));
        player2ShootText.setLayoutX(339+p2o);
        player2ShootText.setLayoutY(807);
        player2ShootText.setPrefWidth(70);

        AtomicInteger player2BulletTypesIndex= new AtomicInteger();
        Text player2BulletTypeText = new Text(bulletTypes.get(0).name());
        player2BulletTypeText.setFont(new Font(50));
        player2BulletTypeText.setFill(Color.WHITE);
        player2BulletTypeText.setLayoutX(497+p2o);
        player2BulletTypeText.setLayoutY(920);
        player2BulletTypeText.setOnMouseClicked(event -> {
            if (bulletTypes.size()-1> player2BulletTypesIndex.get()){
                player2BulletTypesIndex.addAndGet(1);
            }else{
                player2BulletTypesIndex.set(0);
            }
            player2BulletTypeText.setText(String.valueOf(bulletTypes.get(player2BulletTypesIndex.get())));
        });

        Image changePlayer2ShipBtnImg = imageLoader.loadFromResources("changeshipbtn.png", 362, 66);
        ImageView changePlayer2ShipBtn = new ImageView(changePlayer2ShipBtnImg);
        changePlayer2ShipBtn.setLayoutX(123+p2o);
        changePlayer2ShipBtn.setLayoutY(952);
        changePlayer2ShipBtn.setOnMouseClicked(event -> {
            if (GameConfig.SHIP_TEXTURE.size()-1>player2ShipTextureIndex.get()){
                player2ShipTextureIndex.addAndGet(1);

            }else{
                player2ShipTextureIndex.set(0);
            }
            player2ShipTexture.set(GameConfig.SHIP_TEXTURE.get(player2ShipTextureIndex.get()));
            Image player2NewShipImg = null;
            try {
                player2NewShipImg = imageLoader.loadFromResources(player2ShipTexture.get(), 229, 229);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player2ShipImageView.setImage(player2NewShipImg);
        });


        Image startGameImage1 = imageLoader.loadFromResources("startgamebtn1.png", 620*0.8, 157*0.8);
        Image startGameImage2 = imageLoader.loadFromResources("startgamebtn2.png", 620*0.8, 157*0.8);
        Image startGameImage3 = imageLoader.loadFromResources("startgamebtn3.png", 620*0.8, 157*0.8);
        ImageView startGameBtn = new ImageView(startGameImage1);
        startGameBtn.setLayoutX(1388);
        startGameBtn.setLayoutY(68);
        startGameBtn.setOnMouseEntered(event -> startGameBtn.setImage(startGameImage2));
        startGameBtn.setOnMouseExited(event -> startGameBtn.setImage(startGameImage1));
        startGameBtn.setOnMousePressed(event -> startGameBtn.setImage(startGameImage3));
        startGameBtn.setOnMouseReleased(event -> startGameBtn.setImage(startGameImage2));
        startGameBtn.setOnMouseClicked(event -> {
            player1Bindings.put(Movement.FORWARD,KeyCode.getKeyCode(player1ForwardText.getText()));
            player1Bindings.put(Movement.BACKWARDS,KeyCode.getKeyCode(player1BackwardsText.getText()));
            player1Bindings.put(Movement.LEFT,KeyCode.getKeyCode(player1LeftText.getText()));
            player1Bindings.put(Movement.RIGHT,KeyCode.getKeyCode(player1RightText.getText()));
            player1Bindings.put(Movement.ROTATE_LEFT,KeyCode.getKeyCode(player1RLeftText.getText()));
            player1Bindings.put(Movement.ROTATE_RIGHT,KeyCode.getKeyCode(player1RRightText.getText()));
            player1Bindings.put(Movement.SHOOT,KeyCode.getKeyCode(player1ShootText.getText()));

            player2Bindings.put(Movement.FORWARD,KeyCode.getKeyCode(player2ForwardText.getText()));
            player2Bindings.put(Movement.BACKWARDS,KeyCode.getKeyCode(player2BackwardsText.getText()));
            player2Bindings.put(Movement.LEFT,KeyCode.getKeyCode(player2LeftText.getText()));
            player2Bindings.put(Movement.RIGHT,KeyCode.getKeyCode(player2RightText.getText()));
            player2Bindings.put(Movement.ROTATE_LEFT,KeyCode.getKeyCode(player2RLeftText.getText()));
            player2Bindings.put(Movement.ROTATE_RIGHT,KeyCode.getKeyCode(player2RRightText.getText()));
            player2Bindings.put(Movement.SHOOT,KeyCode.getKeyCode(player2ShootText.getText()));
            try {
                gameEngine.initializeNewGame();
                gameEngine.addNewPlayer("Player 1",player1ShipTexture.get(),BulletType.valueOf(player1BulletTypeText.getText()),invertMap(player1Bindings));
                gameEngine.addNewPlayer("Player 2",player2ShipTexture.get(),BulletType.valueOf(player2BulletTypeText.getText()),invertMap(player2Bindings));
                gameEngine.getRootSetter().setRoot(gameEngine.launchGame());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pane.getChildren().add(background);
        pane.getChildren().add(player1ShipImageView);
        pane.getChildren().add(changePlayer1ShipBtn);
        pane.getChildren().add(player1ForwardText);
        pane.getChildren().add(player1BackwardsText);
        pane.getChildren().add(player1LeftText);
        pane.getChildren().add(player1RightText);
        pane.getChildren().add(player1RRightText);
        pane.getChildren().add(player1RLeftText);
        pane.getChildren().add(player1ShootText);
        pane.getChildren().add(player1BulletTypeText);

        pane.getChildren().add(player2ShipImageView);
        pane.getChildren().add(changePlayer2ShipBtn);
        pane.getChildren().add(player2ForwardText);
        pane.getChildren().add(player2BackwardsText);
        pane.getChildren().add(player2LeftText);
        pane.getChildren().add(player2RightText);
        pane.getChildren().add(player2RRightText);
        pane.getChildren().add(player2RLeftText);
        pane.getChildren().add(player2ShootText);
        pane.getChildren().add(player2BulletTypeText);

        pane.getChildren().add(startGameBtn);
        return pane;
    }

    private Map<KeyCode, Movement> invertMap(Map<Movement, KeyCode> bindings) {
        Map<KeyCode,Movement> invertedMap=new HashMap<>();
        for (Movement movement : bindings.keySet()) {
            invertedMap.put(bindings.get(movement),movement);
        }
        return invertedMap;
    }
}
