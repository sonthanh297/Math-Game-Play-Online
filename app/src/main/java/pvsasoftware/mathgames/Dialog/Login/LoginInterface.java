package pvsasoftware.mathgames.Dialog.Login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by thien on 12/25/2016.
 */

public interface LoginInterface {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();
    void handleSignUp();
    void handleSignIn();
    void handleGoogleSignIn(GoogleSignInAccount account);
    void gotoMultiPlayerActivity();
    void loginError(String error);
    void newUserError(String error);
}
