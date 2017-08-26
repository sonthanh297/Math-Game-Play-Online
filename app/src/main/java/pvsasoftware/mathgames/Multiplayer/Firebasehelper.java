package pvsasoftware.mathgames.Multiplayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;

/**
 * Created by thien on 12/25/2016.
 */

public class Firebasehelper {

        private DatabaseReference databaseReference;
        private final static String SEPARATOR = "___";
        private final static String USER_PATH = "users";
        private final static String GAMEDATA_PATH = "gamedata";
        private final static String FRIENDLIST = "multiplayerfriendlist";

    private static class SingletonHolder {
        private static final Firebasehelper INSTANCE = new Firebasehelper();
    }

    public static Firebasehelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Firebasehelper(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }

    public String getCurrentEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null){
            email = user.getEmail();
        }
        return email;
    }

    public DatabaseReference getUser(String email){
        DatabaseReference userReference = null;
        if (email != null){
            String emailkey = email.replace(".","_");
            userReference = databaseReference.getRoot().child(USER_PATH).child(emailkey);
        }
        return userReference;
    }
    public DatabaseReference getPlayGameData(String email) {
        DatabaseReference dataGamePlay = null;
        if (email != null) {
            String emailkey = email.replace(".","_");
            dataGamePlay = databaseReference.getRoot().child(GAMEDATA_PATH).child(emailkey);
        }
        return dataGamePlay;
    }
    public DatabaseReference getCurrentUser(){
        return getUser(getCurrentEmail());
    }

    public DatabaseReference getContact(String email) {
        return getUser(email).child(FRIENDLIST);
    }

    public DatabaseReference getCurrentContact(){
        return getContact(getCurrentEmail());
    }

    public void changeUserConnectionStatus(boolean online) {
        if (getCurrentUser() != null ){
            Map<String,Object> updates = new HashMap<>();
            updates.put("multiplayerstatus",online);
            getCurrentUser().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }

    }

    public DatabaseReference getOneContact(String mainEmail,String childEmail){
        String  childKey = childEmail.replace(".","_");
        return getUser(mainEmail).child(FRIENDLIST).child(childKey);
    }

    public void notifyContactsOfConnectionChange( final boolean online, final boolean signoff){
        final String myEmail = getCurrentEmail();
        getCurrentContact().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    DatabaseReference databaseReference = getOneContact(email,myEmail);
                    databaseReference.setValue(online);
                }
                if (signoff){
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online,false);
    }

    public void signOff(){
        notifyContactsOfConnectionChange(Multiplayer.OFFLINE,true);
        FirebaseAuth.getInstance().signOut();
    }




}
