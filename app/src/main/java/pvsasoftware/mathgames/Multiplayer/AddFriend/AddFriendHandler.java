package pvsasoftware.mathgames.Multiplayer.AddFriend;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pvsasoftware.mathgames.Multiplayer.Firebasehelper;
import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;

/**
 * Created by thien on 12/27/2016.
 */

@SuppressWarnings("ALL")
public class AddFriendHandler implements AddFriendHandlerInterface {
        private AddFriend addFriend;


    public  AddFriendHandler( AddFriend addFriend) {
        this.addFriend = addFriend;
    }

    @Override
    public void addContact(final String email) {
        addFriend.hideInput();
        addFriend.showProgress();
        final String key = email.replace(".","_");
        final Firebasehelper helper = Firebasehelper.getInstance();
        final DatabaseReference userContact = helper.getUser(email);
        userContact.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Multiplayer user = dataSnapshot.getValue(Multiplayer.class);
                if (user !=null) {
                    boolean status = user.getMultiplayerstatus();
                    DatabaseReference myCurrentContact = helper.getCurrentContact();
                    myCurrentContact.child(key).setValue(status);
                    String currentEmail = helper.getCurrentEmail();
                    currentEmail = currentEmail.replace(".","_");
                    DatabaseReference userContact = helper.getContact(email);
                    userContact.child(currentEmail).setValue(true);
                    addFriend.contactAdded();
                } else {
                    addFriend.contactNotAdded();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
