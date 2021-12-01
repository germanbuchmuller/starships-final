package core.menu;

import edu.austral.dissis.starships.file.ImageLoader;
import core.GameMain;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenu implements GameMenu{
    private final ImageLoader imageLoader;
    private final double sf=0.75;
    private final MenuEvent startNewGameEvent,loadGameEvent;

    public MainMenu(MenuEvent startNewGameEvent, MenuEvent loadGameEvent) {
        imageLoader=new ImageLoader();
        this.startNewGameEvent=startNewGameEvent;
        this.loadGameEvent=loadGameEvent;
    }

    public Parent load() throws IOException {
        Pane pane = new Pane();

        Image img = imageLoader.loadFromResources("menu-background.png", 1920*sf, 1080*sf);
        ImageView imageView = new ImageView(img);

        Image newGameImage1 = imageLoader.loadFromResources("newGameBtn1.png", 620*sf, 157*sf);
        Image newGameImage2 = imageLoader.loadFromResources("newGameBtn2.png", 620*sf, 157*sf);
        Image newGameImage3 = imageLoader.loadFromResources("newGameBtn3.png", 620*sf, 157*sf);
        ImageView newGameBtn = new ImageView(newGameImage1);
        newGameBtn.setLayoutX(660*sf);
        newGameBtn.setLayoutY(530*sf);
        setButtonTextures(newGameImage1, newGameImage2, newGameImage3, newGameBtn, startNewGameEvent);


        Image loadGameImage1 = imageLoader.loadFromResources("loadGameBtn1.png", 620*sf, 157*sf);
        Image loadGameImage2 = imageLoader.loadFromResources("loadGameBtn2.png", 620*sf, 157*sf);
        Image loadGameImage3 = imageLoader.loadFromResources("loadGameBtn3.png", 620*sf, 157*sf);
        ImageView loadGameBtn = new ImageView(loadGameImage1);
        loadGameBtn.setLayoutX(660*sf);
        loadGameBtn.setLayoutY(700*sf);
        setButtonTextures(loadGameImage1, loadGameImage2, loadGameImage3, loadGameBtn, loadGameEvent);

        Image quitGameImage1 = imageLoader.loadFromResources("quitGameBtn1.png", 620*sf, 157*sf);
        Image quitGameImage2 = imageLoader.loadFromResources("quitGameBtn2.png", 620*sf, 157*sf);
        Image quitGameImage3 = imageLoader.loadFromResources("quitGameBtn3.png", 620*sf, 157*sf);
        ImageView quitGameBtn = new ImageView(quitGameImage1);
        quitGameBtn.setLayoutX(660*sf);
        quitGameBtn.setLayoutY(870*sf);
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

    private void setButtonTextures(Image newGameImage1, Image newGameImage2, Image newGameImage3, ImageView newGameBtn, MenuEvent startNewGameEvent) {
        newGameBtn.setOnMouseEntered(event -> newGameBtn.setImage(newGameImage2));
        newGameBtn.setOnMouseExited(event -> newGameBtn.setImage(newGameImage1));
        newGameBtn.setOnMousePressed(event -> newGameBtn.setImage(newGameImage3));
        newGameBtn.setOnMouseReleased(event -> newGameBtn.setImage(newGameImage2));
        newGameBtn.setOnMouseClicked(event ->  startNewGameEvent.execute());
    }
}
