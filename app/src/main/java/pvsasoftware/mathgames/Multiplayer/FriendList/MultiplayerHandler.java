package pvsasoftware.mathgames.Multiplayer.FriendList;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.Model.GameType3;
import pvsasoftware.mathgames.Multiplayer.Firebasehelper;
import pvsasoftware.mathgames.Multiplayer.enties.GamePlayDataOnline;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline1;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline2;
import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;

/**
 * Created by thien on 12/27/2016.
 */

public class MultiplayerHandler implements MultiplayerHandlerInterface {

    private Firebasehelper helper;
    private ChildEventListener childEventListener;
    private ValueEventListener toConnectListener;
    private ValueEventListener listenerPlayerConnect;
    private MultiplayerActivityInterface multiplayerActivityInterface;
    private final static String GAMEPLAYDATA = "gameplaydata";
    private final static String GameDATAPLAYER = "playuser";
    private final static String FAKEUSEER = "Fakeuser@gmail.com";
    private List<GameTypeOnline1> gameTypeOnline1s;
    private List<GameTypeOnline2> gameTypeOnline2s;
    private List<GameType3> gameType3s;
    public MultiplayerHandler( MultiplayerActivityInterface multiplayerActivityInterface)
    {

        this.multiplayerActivityInterface = multiplayerActivityInterface;
        helper = Firebasehelper.getInstance();
    }
    private void getContactList(){
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String email = dataSnapshot.getKey();
                        email = email.replace("_",".");
                        boolean status = ((Boolean)dataSnapshot.getValue()).booleanValue();
                        Multiplayer user = new Multiplayer(email,status,null,false,null);
                       postEventForContactlist(ContactListEvent.onContactAdded,user);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_",".");
                    boolean status = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    Multiplayer user = new Multiplayer(email,status,null,false,null);
                    postEventForContactlist(ContactListEvent.onContactChanged,user);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_",".");
                    boolean status = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    Multiplayer user = new Multiplayer(email,status,null,false,null);
                    postEventForContactlist(ContactListEvent.onContactRemoved,user);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        helper.getCurrentContact().addChildEventListener(childEventListener);

    }
    private void destroyContactListListener() {
        childEventListener = null;
    }
    private void unSubscribeForContactList() {
        if (childEventListener != null ) {
            helper.getCurrentContact().removeEventListener(childEventListener);
        }
    }
    private void changeUserConnectionStatus(boolean online){
        helper.changeUserConnectionStatus(online);
    }

    private void postEventForContactlist( int event,Multiplayer user){
        if (multiplayerActivityInterface != null) {
            switch (event){
                case ContactListEvent.onContactAdded:
                    multiplayerActivityInterface.onContactAdded(user);
                    break;
                case ContactListEvent.onContactChanged:
                    multiplayerActivityInterface.onContactChanged(user);
                    break;
                case ContactListEvent.onContactRemoved:
                    multiplayerActivityInterface.onContactRemoved(user);
                    break;

            }
        }
    }


    public void OnlineFakeUser() {
        DatabaseReference fakeReference = helper.getUser(FAKEUSEER);
        Multiplayer fakeUser = new Multiplayer(FAKEUSEER,true,null,true,null);

    }

// listener when have play invite to play game
    private  void listenerPlayerConnected() {
            DatabaseReference currentUser = helper.getCurrentUser();
            if (listenerPlayerConnect == null) {
                listenerPlayerConnect = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Multiplayer user = dataSnapshot.getValue(Multiplayer.class);
                        if (user != null && user.getplayerConnected()) {
                            multiplayerActivityInterface.onPlayerConnected(user.getConnectedPlayer());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
            }
        currentUser.addValueEventListener(listenerPlayerConnect);
    }

    public void setCancelPlayerConnected(String email) {
        DatabaseReference currentUser = helper.getCurrentUser();
        Map<String,Object> updates = new HashMap<>();
        updates.put("playerConnected",false);
        updates.put("connectedPlayer","");
        currentUser.updateChildren(updates);
        String key = getCurrentUserEmail().replace(".","_");
        DatabaseReference dataGamePlay = helper.getPlayGameData(email);
        final DatabaseReference dataGamePlayUser = dataGamePlay.child(GameDATAPLAYER).child(key).child("connect");
        dataGamePlayUser.setValue(false);
    }


    public void invitePlayer(Multiplayer user) {
        final String email = user.getMultiplayerEmail();
        MainHaindler.getInstance().setPlayerConnecting(email);
        final String  key = email.replace(".","_");
        final DatabaseReference playerUser = helper.getUser(email);

        playerUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Multiplayer user = dataSnapshot.getValue(Multiplayer.class);
                if (user != null && !user.getplayerConnected()  && user.getMultiplayerstatus() ) {
                    Map<String,Object> updates = new HashMap<>();
                    updates.put("playerConnected",true);
                    updates.put("connectedPlayer",helper.getCurrentEmail());
                    playerUser.updateChildren(updates);
                    multiplayerActivityInterface.connectPlayer();
                    pushDataGamePlay(key);
                    listenerConnectingPlayer(email);
                } else {
                    multiplayerActivityInterface.cantConnecctPlayer();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //listenerConnectingPlayer(email);

    }
    public void disConnectToPlayer( ) {
        String email = MainHaindler.getInstance().getPlayerConnecting();
        if (email != null) {
            final DatabaseReference playerUser = helper.getUser(email);
            Map<String, Object> updates = new HashMap<>();
            updates.put("playerConnected", false);
            updates.put("connectedPlayer", "");
            playerUser.updateChildren(updates);
        }

    }

    public void initSetData(GamePlayDataOnline gamePlayDataOnline) {
        DatabaseReference dataGamePlay = helper.getPlayGameData(helper.getCurrentEmail()).child(GAMEPLAYDATA);
        dataGamePlay.setValue(gamePlayDataOnline);
    }


    private void pushDataGamePlay(String key) {

        DatabaseReference databasePlayerScore = helper.getPlayGameData(helper.getCurrentEmail()).child("score").child(key);
        databasePlayerScore.setValue(0);
        String currentKey = helper.getCurrentEmail();
        currentKey = currentKey.replace(".","_");
        DatabaseReference databaseYourScore = helper.getPlayGameData(helper.getCurrentEmail()).child("score").child(currentKey);
        databaseYourScore.setValue(0);
        final DatabaseReference dataGamePlayUser = helper.getPlayGameData(helper.getCurrentEmail()).child(GameDATAPLAYER).child(key).child("connect");
        dataGamePlayUser.setValue(false);
    }

    // listener when invite player game , start game if player accept

    private void listenerConnectingPlayer(final String email) {
        String key = email.replace(".","_");
        DatabaseReference dataGamePlay = helper.getPlayGameData(helper.getCurrentEmail());
        final DatabaseReference dataGamePlayUser = dataGamePlay.child(GameDATAPLAYER).child(key).child("connect");
        if (toConnectListener == null ) {
            toConnectListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean status = ((Boolean)dataSnapshot.getValue()).booleanValue();
                    if (status != null && status) {
                        multiplayerActivityInterface.dismisDiaLogConnect();
                        MainHaindler.getInstance().setBossData(true);
                        multiplayerActivityInterface.startGame();
                        MainHaindler.getInstance().setMultiplayer(true);
                        dataGamePlayUser.setValue(false);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }

        if (dataGamePlayUser != null  ) {
            dataGamePlayUser.addValueEventListener(toConnectListener);
        }

    }

    public void acceptToPlay(final String email) {
        String key = getCurrentUserEmail().replace(".","_");
        DatabaseReference dataGamePlay = helper.getPlayGameData(email).child(GAMEPLAYDATA);
        final DatabaseReference dataGamePlayUser =  helper.getPlayGameData(email).child(GameDATAPLAYER).child(key).child("connect");
        dataGamePlayUser.setValue(true);
        MainHaindler.getInstance().setMultiplayer(true);
        dataGamePlay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GamePlayDataOnline gamePlayDataOnline = dataSnapshot.getValue(GamePlayDataOnline.class);
                //GenericTypeIndicator<List<GameTypeOnline1>> t = new GenericTypeIndicator<List<GameTypeOnline1>>() {
              // };
               // gameTypeOnline1s = dataSnapshot.getValue(t);
                gameTypeOnline1s = gamePlayDataOnline.getGameTypeOnline1s();
                gameTypeOnline2s = gamePlayDataOnline.getGameTypeOnline2s();
                List<GameType3> gameType3s = gamePlayDataOnline.getGameType3s();
                MainHaindler.getInstance().setGameTypeOnline1s(gameTypeOnline1s);
                MainHaindler.getInstance().setGameTypeOnline2s(gameTypeOnline2s);
                MainHaindler.getInstance().setGameType3s(gameType3s);
                MainHaindler.getInstance().setPlayerConnecting(email);
                multiplayerActivityInterface.startGame();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onPause() {
            changeUserConnectionStatus(Multiplayer.OFFLINE);
            unSubscribeForContactList();
          DatabaseReference currentUser = helper.getCurrentUser();
        if (listenerPlayerConnect != null && currentUser != null) {
            currentUser.removeEventListener(listenerPlayerConnect);
        }


    }

    private void resetData() {
        DatabaseReference currentUser = helper.getCurrentUser();
        Map<String,Object> updates = new HashMap<>();
        updates.put("playerConnected",false);
        updates.put("connectedPlayer","");
        if (currentUser != null) {
            currentUser.updateChildren(updates);
        }

        MainHaindler.getInstance().setShowDialog(false);
        MainHaindler.getInstance().setConnectedPlayer(false);
        MainHaindler.getInstance().setMultiplayer(false);
        MainHaindler.getInstance().setBossData(false);
    }



    @Override
    public void onResume() {
            resetData();
            changeUserConnectionStatus(Multiplayer.ONLINE);
            getContactList();
            listenerPlayerConnected();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
                resetData();
                destroyContactListListener();
                listenerPlayerConnect = null;
                //multiplayerActivityInterface = null;
    }

    @Override
    public void signOff() {
            destroyContactListListener();
            unSubscribeForContactList();
            helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getCurrentEmail();
    }

    @Override
    public void removeContact(String email) {
            String currentUserEmail = helper.getCurrentEmail();
            helper.getOneContact(email,currentUserEmail).removeValue();
            helper.getOneContact(currentUserEmail,email).removeValue();
    }

    class ContactListEvent {
        public final static int onContactAdded = 0;
        public final static int onContactChanged = 1;
        public final static int onContactRemoved = 2;
    }


}
