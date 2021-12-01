package view.concrete;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import misc.Player;
import view.PlayerStatsView;

public class MyPlayerStatsView implements PlayerStatsView {
    private final Player player;
    private final Text playerNameText;
    private final Text playerPointsText;
    private final Text playerLivesText;
    private final Text playerHealthText;
    private final Text playerBulletTypeText;
    private final int fontSize;

    public MyPlayerStatsView(Player player, double x, double y, int fontSize) {
        this.player = player;
        this.fontSize=fontSize;
        playerNameText=new Text(player.getName());
        playerPointsText=new Text("Points: "+player.getPoints());
        playerLivesText=new Text("Lives: "+player.getLives());
        playerBulletTypeText=new Text("Bullet: "+player.getShip().getBulletType());
        playerHealthText=new Text("Health: "+((player.getShip().getHealth()/player.getShip().getMaxHealth())*100)+"%");
        playerNameText.setLayoutX(x);
        playerNameText.setLayoutY(y);
        playerNameText.setFont(Font.font(fontSize));
        playerNameText.setFill(Color.WHITE);
        playerPointsText.setLayoutX(x);
        playerPointsText.setLayoutY(y+fontSize);
        playerPointsText.setFont(Font.font(fontSize));
        playerPointsText.setFill(Color.WHITE);
        playerBulletTypeText.setLayoutX(x);
        playerBulletTypeText.setLayoutY(y+2*fontSize);
        playerBulletTypeText.setFont(Font.font(fontSize));
        playerBulletTypeText.setFill(Color.WHITE);
        playerLivesText.setLayoutX(x);
        playerLivesText.setLayoutY(y+3*fontSize);
        playerLivesText.setFont(Font.font(fontSize));
        playerLivesText.setFill(Color.RED);
        playerHealthText.setLayoutX(x);
        playerHealthText.setLayoutY(y+4*fontSize);
        playerHealthText.setFont(Font.font(fontSize));
        playerHealthText.setFill(Color.RED);
    }

    public void update(){
        playerNameText.setText(player.getName());
        playerPointsText.setText("Points: "+player.getPoints());
        playerLivesText.setText("Lives: "+player.getLives());
        playerBulletTypeText.setText("Bullet: "+player.getShip().getBulletType());
        double healthPercentage=(double)player.getShip().getHealth() / (double)player.getShip().getMaxHealth();
        playerHealthText.setText("Health: "+(int)(healthPercentage*100)+"%");
    }

    public Node getView(){
        return new Group(playerNameText,playerPointsText,playerHealthText,playerLivesText, playerBulletTypeText);
    }

    @Override
    public double getWidth() {
        return playerNameText.getWrappingWidth();
    }

    @Override
    public double getHeight() {
        return 5*fontSize;
    }
}
