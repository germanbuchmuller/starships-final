package controller;

import engine.GameEngine;
import javafx.scene.input.KeyCode;

public interface MovementEngine extends GameEngine {
    void update(double secondsSinceLastFrame);
    void keyPressed(KeyCode keyCode);
}
