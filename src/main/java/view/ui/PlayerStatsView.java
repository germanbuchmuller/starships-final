package view.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import misc.Player;

public class PlayerStatsView {
    private final Player player;
    private final Text playerNameText;
    private final Text playerPointsText;
    private final Text playerLivesText;
    private double x, y;
    private int fontSize;

    public PlayerStatsView(Player player, double x, double y, int fontSize) {
        this.player = player;
        playerNameText=new Text(player.getName());
        playerPointsText=new Text("Points: "+player.getPoints());
        playerLivesText=new Text("Lives: "+player.getLives());
        this.x=x;
        this.y=y;
        playerNameText.setLayoutX(x);
        playerNameText.setLayoutY(y);
        playerNameText.setFont(Font.font(fontSize));
        playerNameText.setFill(Color.WHITE);
        playerPointsText.setLayoutX(x);
        playerPointsText.setLayoutY(y+fontSize);
        playerPointsText.setFont(Font.font(fontSize));
        playerPointsText.setFill(Color.WHITE);
        playerLivesText.setLayoutX(x);
        playerLivesText.setLayoutY(y+2*fontSize);
        playerLivesText.setFont(Font.font(fontSize));
        playerLivesText.setFill(Color.RED);
        this.fontSize=fontSize;
    }

    public void update(){
        playerNameText.setText(player.getName());
        playerPointsText.setText("Points: "+player.getPoints());
        playerLivesText.setText("Lives: "+player.getLives());
    }

    public Text getPlayerNameText() {
        return playerNameText;
    }

    public Text getPlayerPointsText() {
        return playerPointsText;
    }

    public Text getPlayerLivesText() {
        return playerLivesText;
    }

    public int getFontSize() {
        return fontSize;
    }
}
