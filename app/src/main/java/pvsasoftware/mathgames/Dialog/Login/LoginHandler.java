package pvsasoftware.mathgames.Dialog.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import pvsasoftware.mathgames.Multiplayer.Firebasehelper;
import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;

/**
 * Created by thien on 12/25/2016.
 */

public class LoginHandler implements LoginHandlerInterface {
            private Firebasehelper helper;
            private DatabaseReference myDatabaseReference;
            private  LoginInterface loginInterface;

    public LoginHandler( LoginInterface loginInterface ){
        this.loginInterface = loginInterface;
        helper = Firebasehelper.getInstance();
        myDatabaseReference = helper.getCurrentUser();
    }

    @Override
    public void signUp(final String email, final String password) {
        if (loginInterface != null){
            loginInterface.disableInputs();
            loginInterface.showProgress();
        }
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            signIn(email,password);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    postEvent(LoginEvent.onSignUpError,e.getMessage());
                }
            });
        } catch (Exception e) {
            postEvent(LoginEvent.onSignUpError,e.getMessage());
        }

    }

    @Override
    public void signIn(String email, String password) {
        if (loginInterface != null){
            loginInterface.disableInputs();
            loginInterface.showProgress();
        }
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myDatabaseReference = helper.getCurrentUser();
                            myDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    initSignIn(dataSnapshot);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    postEvent(LoginEvent.onSignInError,databaseError.getMessage());
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    postEvent(LoginEvent.onSignInError,e.getMessage());
                }
            });

        } catch (Exception e) {
            postEvent(LoginEvent.onSignInError,e.getMessage());
        }
    }


    @Override
    public void googleSignIn(GoogleSignInAccount account) {
        if (loginInterface != null){
            loginInterface.disableInputs();
            loginInterface.showProgress();
        }
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myDatabaseReference = helper.getCurrentUser();
                            myDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    initSignIn(dataSnapshot);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    postEvent(LoginEvent.onSignInError,databaseError.getMessage());
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    postEvent(LoginEvent.onSignInError, e.getMessage());
                }
            });

        } catch (Exception e) {
            postEvent(LoginEvent.onSignInError,e.getMessage());
        }



    }

    private void registerNewUser(){
        String email = helper.getCurrentEmail();
        if (email != null) {
            Multiplayer user = new Multiplayer(email,true,null,false,null);
            myDatabaseReference.setValue(user);
        }
    }

    private void initSignIn(DataSnapshot snapshot) {
        Multiplayer currentUser = snapshot.getValue(Multiplayer.class);
        if (currentUser == null) {
            registerNewUser();
        }
        helper.changeUserConnectionStatus(Multiplayer.ONLINE);
        postEvent(LoginEvent.onSignInSuccess,null);
    }


    private void postEvent(int type,String errorMsg) {
            switch (type){
                case LoginEvent.onSignInError:
                    onSignInError(errorMsg);
                    break;
                case LoginEvent.onSignInSuccess:
                    onSignInSuccess();
                    break;
                case LoginEvent.onSignUpError:
                    onSignUpError(errorMsg);
                    break;
                case LoginEvent.onSignUpSuccess:
                    break;
                case LoginEvent.onFailedToRecoverSession:
                    onFailedToRecoverSession();
                    break;
            }
    }

    private void onSignInSuccess() {
        if (loginInterface != null) {
            loginInterface.gotoMultiPlayerActivity();
        }
    }
    private void onSignInError(String error) {
        if (loginInterface != null) {
            loginInterface.hideProgress();
            loginInterface.enableInputs();
            loginInterface.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if (loginInterface != null) {
            loginInterface.hideProgress();
            loginInterface.enableInputs();
            loginInterface.newUserError(error);
        }
    }
    private void onFailedToRecoverSession() {
        if (loginInterface != null) {
            loginInterface.hideProgress();
            loginInterface.enableInputs();
        }
    }





}
