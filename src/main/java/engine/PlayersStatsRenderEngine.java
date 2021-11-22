package engine;

import controller.PlayersController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import view.ui.PlayerStatsView;

import java.util.ArrayList;
import java.util.List;

public class PlayersStatsRenderEngine {
    private final PlayersController playersController;
    private final Pane pane;
    private final List<PlayerStatsView> playerViews;
    private final List<Node> viewsToRemove;
    private boolean initialized;
    private int fontSize = 40;

    public PlayersStatsRenderEngine(PlayersController playersController, Pane pane) {
        this.pane=pane;
        this.playersController = playersController;
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
        for (int i = 0; i < playersController.getPlayers().size(); i++) {
            PlayerStatsView playerView = new PlayerStatsView(playersController.getPlayers().get(i),50,100+(i*3* fontSize*1.15), fontSize);
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
