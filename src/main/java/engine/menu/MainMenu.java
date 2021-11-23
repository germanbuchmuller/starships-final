package engine.menu;

import edu.austral.dissis.starships.file.ImageLoader;
import engine.GameEngine;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenu {
    private final ImageLoader imageLoader;
    private final GameEngine gameEngine;

    public MainMenu(GameEngine gameEngine) {
        imageLoader=new ImageLoader();
        this.gameEngine=gameEngine;
    }

    public Parent load() throws IOException {
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
                gameEngine.getRootSetter().setRoot(gameEngine.loadNewGameMenu());
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
        loadGameBtn.setOnMouseClicked(event -> {

            try {
                gameEngine.loadSavedGame();
                gameEngine.getRootSetter().setRoot(gameEngine.launchGame());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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
}
