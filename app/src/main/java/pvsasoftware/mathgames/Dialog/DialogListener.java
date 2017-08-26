package pvsasoftware.mathgames.Dialog;

/**
 * Created by thien on 12/12/2016.
 */

public interface DialogListener {
    interface HighScore {
        void onSaveHighScore(String name);
        void onCancelHighScore();
    }
    interface MultiplayerInterface {
        void openMultiplayer();
    }

    interface  SignInInterface {
        void openGoogleSignin();
    }


}
