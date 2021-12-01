package core.menu;

import core.GameMain;

public class StartNewGameEvent implements MenuEvent{
    private final GameMain gameMain;

    public StartNewGameEvent(GameMain gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void execute() {
        try{
            gameMain.startNewGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
