package pvsasoftware.mathgames.Dialog.Login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by thien on 12/25/2016.
 */

public interface LoginHandlerInterface {
    void signUp(final String email, final String password);
    void signIn(String email, String password);
    void googleSignIn(GoogleSignInAccount account);


}
