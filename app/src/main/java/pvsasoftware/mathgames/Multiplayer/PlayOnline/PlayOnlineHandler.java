package pvsasoftware.mathgames.Multiplayer.PlayOnline;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.Multiplayer.Firebasehelper;
import pvsasoftware.mathgames.PlayMath1MultiplayerInterface;

/**
 * Created by thien on 1/1/2017.
 */

public class PlayOnlineHandler {
    private Firebasehelper helper;
    private final static String SCORE = "score";
    private PlayMath1MultiplayerInterface playMath1MultiplayerInterface;


    public PlayOnlineHandler(PlayMath1MultiplayerInterface playMath1MultiplayerInterface) {
        helper = Firebasehelper.getInstance();
        this.playMath1MultiplayerInterface = playMath1MultiplayerInterface;
    }


    public void setScore(int score) {
        DatabaseReference scoreData;
        String email,key;
       if (MainHaindler.getInstance().getBossData()) {
            email = helper.getCurrentEmail();
           scoreData = helper.getPlayGameData(email).child(SCORE);
           key = email.replace(".","_");
           scoreData.child(key).setValue(score);
       } else {
            email = MainHaindler.getInstance().getPlayerConnecting();
           if (email != null) {
               scoreData = helper.getPlayGameData(email).child(SCORE);
               key = helper.getCurrentEmail().replace(".","_");
               scoreData.child(key).setValue(score);
           }
       }
    }

    public void getScore() {
        String key;
        DatabaseReference scoreData ;
        if (MainHaindler.getInstance().getBossData()) {
             key = MainHaindler.getInstance().getPlayerConnecting();
            if (key != null) {
                key = key.replace(".","_");
                scoreData = helper.getPlayGameData(helper.getCurrentEmail()).child(SCORE).child(key);
            } else {
                scoreData = null;
            }
        } else {
            String email = MainHaindler.getInstance().getPlayerConnecting();
            if (email != null) {
                key = email.replace(".","_");
                scoreData = helper.getPlayGameData(email).child(SCORE).child(key);
            } else {
                scoreData = null;
            }
        }

        if (scoreData != null ) {
            scoreData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        int score =  dataSnapshot.getValue(Integer.class);
                        playMath1MultiplayerInterface.setMultiplayerEndGame(score);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


}
