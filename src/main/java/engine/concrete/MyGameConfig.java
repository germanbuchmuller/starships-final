package engine.concrete;

import controller.Movement;
import edu.austral.dissis.starships.file.FileLoader;
import engine.GameConfig;
import javafx.scene.input.KeyCode;
import misc.BulletType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGameConfig implements GameConfig {
    public static int PLAYER_LIVES;
    public static int MIN_ASTEROIDS_IN_GAME;
    public static int MAX_ASTEROIDS_IN_GAME;
    public static int ASTEROID_SPEED;

    public static int SHIP_HEALTH;
    public static int SHIP_SPEED;
    public static int SHIP_REWARD_POINTS;
    public static double SHIP_WIDTH;
    public static double SHIP_HEIGHT ;

    public static List<Map<KeyCode, Movement>> playerBindingsList = new ArrayList<>();
    public static List<String> playersSkin = new ArrayList<>();
    public static List<BulletType> playersBulletType = new ArrayList<>();

    public static Map<BulletType, Integer> BULLET_DAMAGES = new HashMap<>();
    public static Map<BulletType, Double> BULLET_SPEED = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_POINTS = new HashMap<>();
    public static Map<BulletType, Double> BULLET_WIDTH = new HashMap<>();
    public static Map<BulletType, Double> BULLET_HEIGHT = new HashMap<>();
    public static Map<BulletType, String> BULLET_TEXTURE = new HashMap<>();
    public static Map<BulletType, Integer> BULLET_MIN_TIME = new HashMap<>();


    public static List<String> SHIP_TEXTURE = new ArrayList<>();

    public MyGameConfig(@NotNull String configFileName) {
        try{
            loadConfig(configFileName);
        }catch (IOException e){
            System.out.println("config file not found");
            System.exit(1);
        }

    }

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
                        BULLET_SPEED.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Double.parseDouble(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_POINTS":
                        BULLET_POINTS.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_WIDTH":
                        BULLET_WIDTH.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Double.parseDouble(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_HEIGHT":
                        BULLET_HEIGHT.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Double.parseDouble(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    case "BULLET_TEXTURE":
                        BULLET_TEXTURE.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),atributeValue.substring(atributeValue.indexOf(",")+1));
                        break;
                    case "BULLET_MIN_TIME":
                        BULLET_MIN_TIME.put(BulletType.valueOf(atributeValue.substring(0,atributeValue.indexOf(","))),Integer.parseInt(atributeValue.substring(atributeValue.indexOf(",")+1)));
                        break;
                    default:
                        if (atributeName.contains("PLAYER")){
                            int playerID = Integer.parseInt(atributeName.substring(6,atributeName.indexOf("_")));
                            if (atributeName.contains("SHIP_TEXTURE")) {
                                System.out.println(playerID+" "+atributeValue);
                                playersSkin.add(playerID,atributeValue);
                            }else if (atributeName.contains("BULLET_TYPE")){
                                System.out.println(playerID+" "+atributeValue);
                                playersBulletType.add(playerID,BulletType.valueOf(atributeValue));
                            }else{
                                if (playerBindingsList.size()>playerID){
                                    Map<KeyCode, Movement> bindings = playerBindingsList.get(playerID);
                                    Movement movement = Movement.valueOf(atributeName.substring(atributeName.indexOf("_")+1));
                                    KeyCode keyCode = KeyCode.getKeyCode(atributeValue);
                                    System.out.println(keyCode+" "+movement);
                                    bindings.put(keyCode,movement);
                                    playerBindingsList.set(playerID,bindings);
                                }else{
                                    Map<KeyCode, Movement> bindings = new HashMap<>();
                                    Movement movement = Movement.valueOf(atributeName.substring(atributeName.indexOf("_")+1));
                                    KeyCode keyCode = KeyCode.getKeyCode(atributeValue);
                                    System.out.println(keyCode+" "+movement);
                                    bindings.put(keyCode,movement);
                                    playerBindingsList.add(bindings);
                                }
                            }
                        }
                }
            }
        }

        System.out.println(playerBindingsList.size());
        System.out.println(playerBindingsList.get(0).get(KeyCode.W));
        System.out.println(playerBindingsList.get(1).get(KeyCode.W));
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

    @Override
    public int getPlayerLives() {
        return PLAYER_LIVES;
    }

    @Override
    public int getMinAsteroidsInGame() {
        return MIN_ASTEROIDS_IN_GAME;
    }

    @Override
    public int getMaxAsteroidsInGame() {
        return MAX_ASTEROIDS_IN_GAME;
    }

    @Override
    public String getAsteroidsTexture() {
        return "asteroid_334$332.png";
    }

    @Override
    public int getShipMaxHealth() {
        return SHIP_HEALTH;
    }

    @Override
    public double getShipMaxSpeed() {
        return SHIP_SPEED;
    }

    @Override
    public double getShipAcceleration() {
        return SHIP_SPEED;
    }

    @Override
    public double getShipWidth() {
        return SHIP_WIDTH;
    }

    @Override
    public double getShipHeight() {
        return SHIP_HEIGHT;
    }

    @Override
    public List<Map<KeyCode, Movement>> getPlayersBindingsList() {
        return playerBindingsList;
    }

    @Override
    public List<String> getPlayerSkinList() {
        return playersSkin;
    }

    @Override
    public List<BulletType> getPlayerBulletTypeList() {
        return playersBulletType;
    }

    @Override
    public Map<BulletType, Integer> getBulletDamages() {
        return BULLET_DAMAGES;
    }

    @Override
    public Map<BulletType, Double> getBulletSpeeds() {
        return BULLET_SPEED;
    }

    @Override
    public Map<BulletType, Integer> getBulletPoints() {
        return BULLET_POINTS;
    }

    @Override
    public Map<BulletType, Double> getBulletWidth() {
        return BULLET_WIDTH;
    }

    @Override
    public Map<BulletType, Double> getBulletHeight() {
        return BULLET_HEIGHT;
    }

    @Override
    public Map<BulletType, String> getBulletTextures() {
        return BULLET_TEXTURE;
    }

    @Override
    public Map<BulletType, Integer> getBulletCoolDown() {
        return BULLET_MIN_TIME;
    }
}
