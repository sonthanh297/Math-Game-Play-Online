package pvsasoftware.mathgames.Multiplayer.FriendList;

import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;

/**
 * Created by thien on 12/27/2016.
 */

public interface MultiplayerActivityInterface {
    void onContactAdded(Multiplayer user);
    void onContactChanged(Multiplayer user);
    void onContactRemoved(Multiplayer user);
    void onPlayerConnected(String email);
    void cantConnecctPlayer();
    void connectPlayer();
    void startGame();
    void dismisDiaLogConnect();
}
