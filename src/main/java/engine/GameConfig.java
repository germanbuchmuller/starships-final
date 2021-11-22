package engine;

import misc.BulletType;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {
    public static int PLAYER_LIVES = 3;
    public static int MIN_ASTEROIDS_IN_GAME = 3;
    public static int MAX_ASTEROIDS_IN_GAME = 6;
    public static int ASTEROID_SPEED = 250;
    public static int SHIP_HEALTH = 100;
    public static int SHIP_SPEED = 200;
    public static int SHIP_REWARD_POINTS = 10;
    public static double SHIP_WIDTH = 60;
    public static double SHIP_HEIGHT = 60;

    public static Map<BulletType, Integer> BULLET_DAMAGES = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_SPEED = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_POINTS = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_WIDTH = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_HEIGHT = new HashMap<>();
    public static Map<BulletType, String> BULLET_TEXTURE = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_MIN_TIME = new HashMap<>();

    public static void loadConfig() {
        BULLET_DAMAGES.put(BulletType.SMALL,10);
        BULLET_DAMAGES.put(BulletType.MEDIUM,15);
        BULLET_DAMAGES.put(BulletType.LARGE,20);
        BULLET_DAMAGES.put(BulletType.ATOMIC,25);

        BULLET_SPEED.put(BulletType.SMALL,400);
        BULLET_SPEED.put(BulletType.MEDIUM,300);
        BULLET_SPEED.put(BulletType.LARGE,200);
        BULLET_SPEED.put(BulletType.ATOMIC,100);

        BULLET_POINTS.put(BulletType.SMALL,1);
        BULLET_POINTS.put(BulletType.MEDIUM,2);
        BULLET_POINTS.put(BulletType.LARGE,3);
        BULLET_POINTS.put(BulletType.ATOMIC,4);

        BULLET_WIDTH.put(BulletType.SMALL,20);
        BULLET_WIDTH.put(BulletType.MEDIUM,30);
        BULLET_WIDTH.put(BulletType.LARGE,40);
        BULLET_WIDTH.put(BulletType.ATOMIC,50);

        BULLET_HEIGHT.put(BulletType.SMALL,61);
        BULLET_HEIGHT.put(BulletType.MEDIUM,91);
        BULLET_HEIGHT.put(BulletType.LARGE,122);
        BULLET_HEIGHT.put(BulletType.ATOMIC,152);

        BULLET_TEXTURE.put(BulletType.SMALL,"projectile.png");
        BULLET_TEXTURE.put(BulletType.MEDIUM,"projectile.png");
        BULLET_TEXTURE.put(BulletType.LARGE,"projectile.png");
        BULLET_TEXTURE.put(BulletType.ATOMIC,"projectile.png");

        BULLET_MIN_TIME.put(BulletType.SMALL,200);
        BULLET_MIN_TIME.put(BulletType.MEDIUM,350);
        BULLET_MIN_TIME.put(BulletType.LARGE,600);
        BULLET_MIN_TIME.put(BulletType.ATOMIC,1200);
    }
}
