package core.concrete;

import controller.visitor.GameState;
import controller.visitor.concrete.MyGameState;
import core.GameConfig;
import core.GameSaver;
import misc.Player;
import misc.Weapon;
import misc.concrete.MyWeapon;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.factory.ProjectileFactory;
import org.jetbrains.annotations.NotNull;
import serialize.concrete.SerializedAsteroid;
import serialize.SerializedEntity;
import serialize.concrete.SerializedPlayer;
import serialize.concrete.SerializedProjectile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyGameSaver implements GameSaver {
    private GameState gameState;
    private final GameConfig gameConfig;
    private final ProjectileFactory projectileFactory;

    public MyGameSaver(GameConfig gameConfig, ProjectileFactory projectileFactory) {
        this.gameConfig = gameConfig;
        this.projectileFactory=projectileFactory;
    }

    @Override
    public GameState initialize() {
        gameState=new MyGameState();
        return gameState;
    }

    @Override
    public void saveGame(@NotNull GameState gameState) throws IOException {
        this.gameState=gameState;

        FileOutputStream playersFOS = new FileOutputStream("players.savegame");
        ObjectOutputStream playersOOS = new ObjectOutputStream(playersFOS);
        writeSerializedPlayers(getSerializedPlayers(),playersOOS);
        playersFOS.close();

        FileOutputStream asteroidsFOS = new FileOutputStream("asteroids.savegame");
        ObjectOutputStream asteroidsOOS = new ObjectOutputStream(asteroidsFOS);
        writeSerializedEntities(getSerializedAsteroids(),asteroidsOOS);
        asteroidsOOS.close();

        FileOutputStream projectilesFOS = new FileOutputStream("projectiles.savegame");
        ObjectOutputStream projectilesOOS = new ObjectOutputStream(projectilesFOS);
        writeSerializedEntities(getSerializedProjectiles(),projectilesOOS);
        projectilesOOS.close();

        System.out.println("Game saved");
    }

    private void writeSerializedEntities(List<SerializedEntity> serializedEntities, ObjectOutputStream entitiesOOS) throws IOException {
        for (SerializedEntity serializedEntity : serializedEntities) {
            entitiesOOS.writeObject(serializedEntity);
        }
        entitiesOOS.close();
    }


    private void writeSerializedPlayers(List<SerializedPlayer> serializedPlayers, ObjectOutputStream playersOOS) throws IOException {
        for (SerializedPlayer serializedPlayer : serializedPlayers) {
            playersOOS.writeObject(serializedPlayer);
        }
        playersOOS.close();
    }

    private List<SerializedPlayer> getSerializedPlayers() {
        List<SerializedPlayer> serializedPlayers= new ArrayList<>();
        for (Player player : gameState.getPlayers()) {
            serializedPlayers.add(new SerializedPlayer(player));
        }
        return serializedPlayers;
    }

    private List<SerializedEntity> getSerializedAsteroids(){
        List<SerializedEntity> serializedEntities= new ArrayList<>();
        for (Asteroid asteroid : gameState.getAsteroids()) {
            serializedEntities.add(new SerializedAsteroid(asteroid));
        }
        return serializedEntities;
    }

    private List<SerializedEntity> getSerializedProjectiles(){
        List<SerializedEntity> serializedEntities= new ArrayList<>();
        for (Projectile projectile : gameState.getProjectiles()) {
            serializedEntities.add(new SerializedProjectile(projectile));
        }
        return serializedEntities;
    }


    @Override
    public void loadGame() throws Exception {
        FileInputStream playersFIS = new FileInputStream("players.savegame");
        ObjectInputStream playersOIS = new ObjectInputStream(playersFIS);
        for (Player player : getPlayers(playersOIS)) {
            gameState.addPlayer(player);
        }
        playersFIS.close();
        FileInputStream asteroidsFIS = new FileInputStream("asteroids.savegame");
        ObjectInputStream asteroidsOIS = new ObjectInputStream(asteroidsFIS);
        for (Asteroid asteroid : getAsteroids(asteroidsOIS)) {
            asteroid.accept(gameState);
        }
        asteroidsFIS.close();

        FileInputStream projectilesFIS = new FileInputStream("projectiles.savegame");
        ObjectInputStream projectilesOIS = new ObjectInputStream(projectilesFIS);
        for (Projectile projectile : getProjectiles(projectilesOIS)) {
            projectile.accept(gameState);
        }
        projectilesFIS.close();

    }

    private List<Player> getPlayers(ObjectInputStream playersOIS) throws IOException {
        SerializedPlayer serializedPlayer;
        List<Player> players = new ArrayList<>();
        try{
            while ((serializedPlayer = (SerializedPlayer)playersOIS.readObject())!=null){
                players.add(createPlayer(serializedPlayer));
            }
        }catch (Exception ignored){}
        playersOIS.close();
        return players;
    }

    private Player createPlayer(SerializedPlayer serializedPlayer){
        Player player=serializedPlayer.toPlayer();
        Weapon weapon = new MyWeapon(gameConfig.getBulletCoolDown(),projectileFactory,player.getId());
        weapon.setBulletType(serializedPlayer.getBulletType());
        player.getShip().setWeapon(weapon);
        return player;
    }

    private List<Asteroid> getAsteroids(ObjectInputStream asteroidsOIS) throws IOException {
        SerializedAsteroid serializedAsteroid;
        List<Asteroid> asteroids=new ArrayList<>();
        try {
            while ((serializedAsteroid = (SerializedAsteroid) asteroidsOIS.readObject()) != null) {
                asteroids.add(serializedAsteroid.toEntity());
            }
        }catch (Exception ignored){}
        asteroidsOIS.close();
        return asteroids;
    }

    private List<Projectile> getProjectiles(ObjectInputStream projectilesOIS) throws IOException {
        SerializedProjectile serializedAsteroid;
        List<Projectile> asteroids=new ArrayList<>();
        try {
            while ((serializedAsteroid = (SerializedProjectile) projectilesOIS.readObject()) != null) {
                asteroids.add(serializedAsteroid.toEntity());
            }
        }catch (Exception ignored){}
        projectilesOIS.close();
        return asteroids;
    }
}
