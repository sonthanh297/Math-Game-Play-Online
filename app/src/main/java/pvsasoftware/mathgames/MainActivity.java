package pvsasoftware.mathgames;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import pvsasoftware.mathgames.Fragment.Fragment_menu;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private AdRequest adRequest;
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMenuFrament();
            }
        },1000);
      //  loadAdd();
    }

    @Override
    protected void onResume() {
        disConnectGoogleAPI();
        super.onResume();
    }

    @Override
    protected void onStart() {
        disConnectGoogleAPI();
        super.onStart();
    }

    //    private void loadAdd() {
//        adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("90BBDFD007516E589EDD85245F36903B").build();
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_full));
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                super.onAdLeftApplication();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//        });
//        interstitialAd.loadAd(adRequest);
//    }
    private void showMenuFrament() {
        Fragment_menu fragment_menu = new Fragment_menu();
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_main, fragment_menu).commit();

    }
    private void disConnectGoogleAPI() {
        if (MainHaindler.getInstance().getClient() != null) {
            GoogleApiClient mGoogleApiClient = MainHaindler.getInstance().getClient();
            mGoogleApiClient.disconnect();
        }
    }

    public GoogleApiClient getGoogleApi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }



    @Override
    public void onBackPressed() {
        //interstitialAd.show();
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
