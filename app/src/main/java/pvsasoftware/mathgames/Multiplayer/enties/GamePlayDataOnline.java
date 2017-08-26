package pvsasoftware.mathgames.Multiplayer.enties;

import java.util.List;

import pvsasoftware.mathgames.Model.GameType3;

/**
 * Created by thien on 12/31/2016.
 */

public class GamePlayDataOnline {
    public List<GameTypeOnline1> gameTypeOnline1s;
    public List<GameTypeOnline2> gameTypeOnline2s;
    public List<GameType3> gameType3s;


    public GamePlayDataOnline() {

    }
    public GamePlayDataOnline(List<GameTypeOnline1> gameTypeOnline1s,List<GameTypeOnline2> gameTypeOnline2s,List<GameType3> gameType3s) {
        this.gameTypeOnline1s = gameTypeOnline1s;
        this.gameTypeOnline2s = gameTypeOnline2s;
        this.gameType3s = gameType3s;
    }

    public List<GameType3> getGameType3s() {
        return gameType3s;
    }

    public List<GameTypeOnline2> getGameTypeOnline2s() {
        return gameTypeOnline2s;
    }

    public List<GameTypeOnline1> getGameTypeOnline1s() {
        return gameTypeOnline1s;
    }
}
