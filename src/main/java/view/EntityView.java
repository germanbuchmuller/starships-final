package view;

import javafx.scene.Node;
import model.Entity;

public interface EntityView {
    void update();
    Node getView();
    Entity getEntity();
}
