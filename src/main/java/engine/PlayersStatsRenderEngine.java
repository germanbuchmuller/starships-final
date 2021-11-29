package engine;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import misc.PlayersRepository;
import org.jetbrains.annotations.NotNull;
import view.ui.PlayerStatsView;

import java.util.ArrayList;
import java.util.List;

public class PlayersStatsRenderEngine {
    private final PlayersRepository playersRepository;
    private final Pane pane;
    private final List<PlayerStatsView> playerViews;
    private final List<Node> viewsToRemove;
    private boolean initialized;
    private final int fontSize = 40;

    public PlayersStatsRenderEngine(@NotNull PlayersRepository playersRepository, Pane pane) {
        this.pane=pane;
        this.playersRepository = playersRepository;
        viewsToRemove=new ArrayList<>();
        playerViews=new ArrayList<>();
    }

    public void checkPlayerControllerUpdates(){
        for (PlayerStatsView playerView : playerViews) {
            viewsToRemove.add(playerView.getPlayerNameText());
            viewsToRemove.add(playerView.getPlayerPointsText());
            viewsToRemove.add(playerView.getPlayerLivesText());
        }
        playerViews.clear();
        pane.getChildren().removeAll(viewsToRemove);
        viewsToRemove.clear();
        for (int i = 0; i < playersRepository.getPlayers().size(); i++) {
            PlayerStatsView playerView = new PlayerStatsView(playersRepository.getPlayers().get(i),50,100+(i*3* fontSize*1.15), fontSize);
            playerViews.add(playerView);
            pane.getChildren().add(playerView.getPlayerNameText());
            pane.getChildren().add(playerView.getPlayerPointsText());
            pane.getChildren().add(playerView.getPlayerLivesText());
        }
        initialized=true;
    }

    public void update(){
        if (!initialized){
            checkPlayerControllerUpdates();
        }
        for (PlayerStatsView playerView : playerViews) {
            playerView.update();
        }
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
