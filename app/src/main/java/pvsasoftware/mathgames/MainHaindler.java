package pvsasoftware.mathgames;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import pvsasoftware.mathgames.Model.GameType3;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline1;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline2;

/**
 * Created by thien on 12/9/2016.
 */
public class MainHaindler {
    private static MainHaindler ourInstance = new MainHaindler();
    private Boolean isMultiplayer = false;
    private List<GameTypeOnline1> gameTypeOnline1s = new ArrayList<>();
    private List<GameTypeOnline2> gameTypeOnline2s = new ArrayList<>();
    private List<GameType3> gameType3s = new ArrayList<>();
    private boolean isShowDialog = false;
    private boolean isConnectedPlayer = false;
    private String  playerConnecting = null;
    private Boolean isBossData = false;
    private GoogleApiClient mGoogleApiClient;

    private Boolean isFakeUser = false;
    public static MainHaindler getInstance() {
        return ourInstance;
    }

    private MainHaindler() {

    }

    public List<GameType3> getGameType3s() {
        return gameType3s;
    }

    public void setGameType3s(List<GameType3> gameType3s) {
        this.gameType3s = gameType3s;
    }

    public Boolean getBossData() {
        return isBossData;
    }

    public void setBossData(Boolean bossData) {
        isBossData = bossData;
    }

    public String getPlayerConnecting() {
        return playerConnecting;
    }

    public void setPlayerConnecting(String playerConnecting) {
        this.playerConnecting = playerConnecting;
    }

    public List<GameTypeOnline2> getGameTypeOnline2s() {
        return gameTypeOnline2s;
    }

    public void setGameTypeOnline2s(List<GameTypeOnline2> gameTypeOnline2s) {
        this.gameTypeOnline2s = gameTypeOnline2s;
    }

    public List<GameTypeOnline1> getGameTypeOnline1s() {
        return gameTypeOnline1s;
    }

    public void setGameTypeOnline1s(List<GameTypeOnline1> gameTypeOnline1s) {
        this.gameTypeOnline1s = gameTypeOnline1s;
    }

    public void setConnectedPlayer(boolean connectedPlayer) {
        isConnectedPlayer = connectedPlayer;
    }

    public boolean isConnectedPlayer() {
        return isConnectedPlayer;
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public void setMultiplayer(Boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public Boolean getMultiplayer() {
        return isMultiplayer;
    }
    public void setClient(GoogleApiClient client){
        mGoogleApiClient = client;
    }

    public GoogleApiClient getClient(){
        return mGoogleApiClient;
    }

    public void setFakeUser(Boolean fakeUser) {
        isFakeUser = fakeUser;
    }

    public Boolean getFakeUser() {
        return isFakeUser;
    }
}
