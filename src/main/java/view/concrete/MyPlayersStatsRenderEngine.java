package view.concrete;

import misc.PlayersRepository;
import model.concrete.Asteroid;
import model.concrete.Projectile;
import model.concrete.Ship;
import org.jetbrains.annotations.NotNull;
import view.GameWindow;
import view.PlayerStatsRenderEngine;
import view.PlayerStatsView;

import java.util.ArrayList;
import java.util.List;

public class MyPlayersStatsRenderEngine implements PlayerStatsRenderEngine {
    private final PlayersRepository playersRepository;
    private final GameWindow gameWindow;
    private final List<PlayerStatsView> playerViews;
    private final List<PlayerStatsView> viewsToRemove;

    public MyPlayersStatsRenderEngine(@NotNull PlayersRepository playersRepository, GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        this.playersRepository = playersRepository;
        viewsToRemove=new ArrayList<>();
        playerViews=new ArrayList<>();
    }

    private void checkPlayerControllerUpdates(){
        viewsToRemove.addAll(playerViews);
        playerViews.clear();
        for (PlayerStatsView myPlayerStatsView : viewsToRemove) {
            gameWindow.removeView(myPlayerStatsView);
        }
        viewsToRemove.clear();
        double height=0;
        double width=0;
        for (int i = 0; i < playersRepository.getPlayers().size(); i++) {
            int fontSize = 35;
            MyPlayerStatsView playerView = new MyPlayerStatsView(playersRepository.getPlayers().get(i),50+(0*width *1.15),70+(i*height *1.15), fontSize);
            height=playerView.getHeight();
            width=playerView.getHeight();
            playerView.update();
            playerViews.add(playerView);
            gameWindow.addView(playerView);
        }
    }

    public void update(){
        checkPlayerControllerUpdates();
    }


    @Override
    public void added(Ship ship) {

    }

    @Override
    public void added(Asteroid asteroid) {

    }

    @Override
    public void added(Projectile projectile) {

    }

    @Override
    public void removed(Ship ship) {

    }

    @Override
    public void removed(Asteroid asteroid) {

    }

    @Override
    public void removed(Projectile projectile) {

    }

    @Override
    public int getViewCount() {
        return playersRepository.getPlayers().size();
    }
}
