package view;

import javafx.scene.layout.Pane;

import java.util.List;

public interface GameWindow {
    double getWidth();
    double getHeight();
    void addViewOnTop(EntityView entityView);
    void addViewOnBack(EntityView entityView);
    void addView(PlayerStatsView playerStatsView);
    void removeView(EntityView entityView);
    void removeView(PlayerStatsView playerStatsView);
    void removeViews(List<EntityView> views);
    Pane init();
}
