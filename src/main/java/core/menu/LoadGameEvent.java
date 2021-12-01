package core.menu;

import core.GameMain;

public class LoadGameEvent implements MenuEvent{
    private final GameMain gameMain;

    public LoadGameEvent(GameMain gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void execute() {
        try{
            gameMain.loadGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
