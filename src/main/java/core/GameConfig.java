package core;

import controller.Movement;
import javafx.scene.input.KeyCode;
import misc.BulletType;

import java.util.List;
import java.util.Map;

public interface GameConfig {
    int getPlayerLives();
    int getMinAsteroidsInGame();
    int getMaxAsteroidsInGame();
    String getAsteroidsTexture();
    int getShipMaxHealth();
    double getShipMaxSpeed();
    double getShipAcceleration();
    double getShipWidth();
    double getShipHeight();
    List<Map<KeyCode, Movement>> getPlayersBindingsList();
    List<String> getPlayerSkinList();
    List<BulletType> getPlayerBulletTypeList();
    Map<BulletType, Integer> getBulletDamages();
    Map<BulletType, Double> getBulletSpeeds();
    Map<BulletType, Integer> getBulletPoints();
    Map<BulletType, Double> getBulletWidth();
    Map<BulletType, Double> getBulletHeight();
    Map<BulletType, String> getBulletTextures();
    Map<BulletType, Integer> getBulletCoolDown();
}
