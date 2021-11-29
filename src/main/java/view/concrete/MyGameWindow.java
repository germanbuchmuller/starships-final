package view.concrete;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import view.EntityView;
import view.GameWindow;
import view.ui.PlayerStatsView;

import java.util.List;

public class MyGameWindow implements GameWindow {
    private  final Pane pane;

    public MyGameWindow() {
        pane=new Pane();
    }

    @Override
    public double getWidth() {
        return pane.getLayoutBounds().getWidth();
    }

    @Override
    public double getHeight() {
        return pane.getLayoutBounds().getHeight();
    }

    @Override
    public void addView(EntityView entityView) {
        addView(entityView.getView());
    }

    @Override
    public void addView(PlayerStatsView playerStatsView) {
        addView(playerStatsView.getPlayerLivesText());
        addView(playerStatsView.getPlayerPointsText());
        addView(playerStatsView.getPlayerNameText());
    }

    @Override
    public void removeView(EntityView entityView) {
        removeView(entityView.getView());
    }

    @Override
    public void removeView(PlayerStatsView playerStatsView) {
        removeView(playerStatsView.getPlayerLivesText());
        removeView(playerStatsView.getPlayerPointsText());
        removeView(playerStatsView.getPlayerNameText());
    }

    @Override
    public void removeViews(List<EntityView> views) {
        for (EntityView view : views) {
            pane.getChildren().remove(view.getView());
        }
    }

    private void addView(Node node){
        pane.getChildren().add(0,node);
    }

    private void removeView(Node node){
        pane.getChildren().remove(node);
    }

    @Override
    public Pane init() {
        return pane;
    }
}
