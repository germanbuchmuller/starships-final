package view;

import javafx.scene.layout.Pane;
import view.ui.PlayerStatsView;

import java.util.List;

public interface GameWindow {
    double getWidth();
    double getHeight();
    void addView(EntityView entityView);
    void addView(PlayerStatsView playerStatsView);
    void removeView(EntityView entityView);
    void removeView(PlayerStatsView playerStatsView);
    void removeViews(List<EntityView> views);
    Pane init();
}
