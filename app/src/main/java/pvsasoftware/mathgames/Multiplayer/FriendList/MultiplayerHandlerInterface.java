package pvsasoftware.mathgames.Multiplayer.FriendList;

/**
 * Created by thien on 12/27/2016.
 */

public interface MultiplayerHandlerInterface {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);

}
