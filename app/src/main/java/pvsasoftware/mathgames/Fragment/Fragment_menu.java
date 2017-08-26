package pvsasoftware.mathgames.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pvsasoftware.mathgames.Dialog.DialogListener;
import pvsasoftware.mathgames.Dialog.Login.Dialog_signin;
import pvsasoftware.mathgames.HighScoreActivity;
import pvsasoftware.mathgames.MainActivity;
import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.Multiplayer.FriendList.MultiplayerActivity;
import pvsasoftware.mathgames.OptionActivity;
import pvsasoftware.mathgames.PlayMath1Activity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/1/2016.
 */

public class Fragment_menu extends Fragment implements View.OnClickListener , DialogListener.MultiplayerInterface , DialogListener.SignInInterface {
    private Button bt_startgame,bt_highscore,bt_multiplayer,bt_option;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Dialog_signin dialog_signin;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_menu,container,false);
        bt_startgame = (Button) rootView.findViewById(R.id.bt_startgame);
        bt_highscore = (Button) rootView.findViewById(R.id.bt_highscore);
        bt_multiplayer = (Button) rootView.findViewById(R.id.bt_multiplayer);
        bt_option = (Button) rootView.findViewById(R.id.bt_option);
        setEvent();
        setAnimation();
        return  rootView;


    }
    private void setEvent() {
        bt_startgame.setOnClickListener(this);
        bt_highscore.setOnClickListener(this);
        bt_multiplayer.setOnClickListener(this);
        bt_option.setOnClickListener(this);
    }
    private  void setAnimation() {
        Animation animationLeft = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_button_menu_left_start);
        Animation animationRight = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_button_menu_right_start);
        bt_startgame.setAnimation(animationLeft);
        bt_multiplayer.setAnimation(animationRight);
        bt_highscore.setAnimation(animationLeft);
        bt_option.setAnimation(animationRight);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                    case R.id.bt_startgame:
                        startGame();
                        break;
                    case R.id.bt_highscore:
                        startHighScore();
                        break;
                    case R.id.bt_multiplayer:
                        startMultiplayer();
                        break;
                    case R.id.bt_option:
                        openOption();
                        break;
                    default:
                        break;

                }
    }

    //    @Override
//    public void onClick(final View view) {
//        view.clearAnimation();
//        Animation zoom_in = AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in);
//        zoom_in.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                switch (view.getId()) {
//                    case R.id.bt_startgame:
//                        startGame();
//                        break;
//                    case R.id.bt_highscore:
//                        startHighScore();
//                        break;
//                    default:
//                        break;
//
//
//                }
//                view.clearAnimation();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.setAnimation(zoom_in);
//
//    }
    private void startGame() {
        Intent intent = new Intent(getActivity(),PlayMath1Activity.class);
        startActivity(intent);
    }
    private void startHighScore() {
        Intent intent = new Intent(getActivity(),HighScoreActivity.class);
        startActivity(intent);
    }
    private void openOption() {
        Intent intent = new Intent(getActivity(),OptionActivity.class);
        startActivity(intent);
    }
    private void startMultiplayer() {
        if (isNetworkAvailable()) {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            if (mFirebaseUser == null) {
                dialog_signin = new Dialog_signin(getActivity(),this,this);
                dialog_signin.show();
                // Log.i("dfdf",mFirebaseUser.getUid());
            } else {
                openMultiplayer();
            }
        } else {
            new AlertDialog.Builder(getActivity()).setTitle(R.string.networktitle)
                    .setTitle(R.string.networkMessage)

                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }


    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void openMultiplayer() {
        Intent intent = new Intent(getActivity(),MultiplayerActivity.class);
        startActivity(intent);
        //Log.i("dfdf",mFirebaseUser.getUid());
    }

    @Override
    public void openGoogleSignin() {
        GoogleApiClient mGoogleApiClient;
        if (MainHaindler.getInstance().getClient() != null) {
            mGoogleApiClient = MainHaindler.getInstance().getClient();
        } else {
            MainActivity activity = (MainActivity)getActivity();
            mGoogleApiClient = activity.getGoogleApi();
            MainHaindler.getInstance().setClient(mGoogleApiClient);
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                dialog_signin.handleGoogleSignIn(account);

            } else {

            }
        }


    }
}
