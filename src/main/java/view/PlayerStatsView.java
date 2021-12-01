package view;

import javafx.scene.Node;

public interface PlayerStatsView {
    void update();
    Node getView();
    double getWidth();
    double getHeight();
}
