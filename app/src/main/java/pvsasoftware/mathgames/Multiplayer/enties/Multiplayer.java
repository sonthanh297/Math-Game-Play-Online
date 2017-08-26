package pvsasoftware.mathgames.Multiplayer.enties;

import java.util.Map;

/**
 * Created by thien on 12/21/2016.
 */

public class Multiplayer {

    private String multiplayeremail;
    private boolean multiplayerstatus;
    private boolean playerConnected ;
    private String connectedPlayer ;
    private Map<String,Boolean> multiplayerfriendlist  ;
    public final static boolean ONLINE = true;
    public final static boolean OFFLINE = false;
    public Multiplayer(){

    }

    public Multiplayer( String multiplayeremail,boolean multiplayerstatus, Map<String,Boolean> multiplayerfriendlist,boolean playerConnected,String connectedPlayer) {

        this.multiplayeremail = multiplayeremail;
        this.multiplayerstatus = multiplayerstatus;
        this.multiplayerfriendlist = multiplayerfriendlist;
        this.playerConnected = playerConnected;
        this.connectedPlayer = connectedPlayer;
    }

    public boolean getMultiplayerstatus() {
        return multiplayerstatus;
    }

    public boolean getplayerConnected() {
        return playerConnected;
    }

    public String getConnectedPlayer() {
        return connectedPlayer;
    }

    public Map<String, Boolean> getMultiplayerfriendlist() {
        return multiplayerfriendlist;
    }

    public void setMultiplayerstatus(boolean status) {
        this.multiplayerstatus = status;
    }




    public void setMultiplayerEmail(String email) {
        this.multiplayeremail = email;
    }

    public String getMultiplayerEmail() {
        return multiplayeremail;
    }


}
