package engine;

import edu.austral.dissis.starships.file.FileLoader;
import misc.BulletType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameConfig {
    public static int PLAYER_LIVES;
    public static int MIN_ASTEROIDS_IN_GAME;
    public static int MAX_ASTEROIDS_IN_GAME;
    public static int ASTEROID_SPEED;

    public static int SHIP_HEALTH;
    public static int SHIP_SPEED;
    public static int SHIP_REWARD_POINTS;
    public static double SHIP_WIDTH;
    public static double SHIP_HEIGHT ;

    public static Map<BulletType, Integer> BULLET_DAMAGES = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_SPEED = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_POINTS = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_WIDTH = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_HEIGHT = new HashMap<>();
    public static Map<BulletType, String> BULLET_TEXTURE = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_MIN_TIME = new HashMap<>();


    public static List<String> SHIP_TEXTURE = new ArrayList<>();

    public static void loadConfig(@NotNull String configFileName) throws IOException {
        FileLoader fileLoader = new FileLoader();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileLoader.loadFromResources(configFileName)));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.equals("-")){
                String atributeName = line.substring(0,line.indexOf(":"));
                String atributeValue = line.substring(line.indexOf(":")+1);
                switch (atributeName){
                    case "PLAYER_LIVES":
                        PLAYER_LIVES=Integer.parseInt(atributeValue);
                        break;
                    case "MIN_ASTEROIDS_IN_GAME":
                        MIN_ASTEROIDS_IN_GAME=Integer.parseInt(atributeValue);
                        break;
                    case "MAX_ASTEROIDS_IN_GAME":
                        MAX_ASTEROIDS_IN_GAME=Integer.parseInt(atributeValue);
                        break;
                    case "ASTEROID_SPEED":
                        ASTEROID_SPEED=Integer.parseInt(atributeValue);
                        break;
                    case "SHIP_HEALTH":
                        SHIP_HEALTH=Integer.parseInt(atributeValue);
                        break;
                    case "SHIP_SPEED":
                        SHIP_SPEED=Integer.parseInt(atributeValue);
                        break;
                    case "SHIP_REWARD_POINTS":
                        SHIP_REWARD_POINTS=Integer.parseInt(atributeValue);
                        break;
                    case "SHIP_WIDTH":
                        SHIP_WIDTH=Double.parseDouble(atributeValue);
                        break;
                    case "SHIP_HEIGHT":
                        SHIP_HEIGHT=Double.parseDouble(atributeValue);
                        break;
                    case "SHIP_TEXTURE":
                        SHIP_TEXTURE.add(atributeValue);
                        break;
                    case "BULLET_DAMAGES":
                        BULLET_DAMAGES.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_SPEED":
                        BULLET_SPEED.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_POINTS":
                        BULLET_POINTS.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_WIDTH":
                        BULLET_WIDTH.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_HEIGHT":
                        BULLET_HEIGHT.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_TEXTURE":
                        BULLET_TEXTURE.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),atributeValue.substring(atributeValue.indexOf(",")+1));
                        break;
                    case "BULLET_MIN_TIME":
                        BULLET_MIN_TIME.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                }
            }
        }
        /*
        System.out.println(SHIP_HEALTH);
        System.out.println(BULLET_DAMAGES.get(BulletType.SMALL));
        System.out.println(BULLET_SPEED.get(BulletType.SMALL));
        System.out.println(BULLET_POINTS.get(BulletType.SMALL));
        System.out.println(BULLET_WIDTH.get(BulletType.SMALL));
        System.out.println(BULLET_HEIGHT.get(BulletType.SMALL));
        System.out.println(BULLET_TEXTURE.get(BulletType.SMALL));
        System.out.println(BULLET_MIN_TIME.get(BulletType.SMALL));
        */
    }
}
