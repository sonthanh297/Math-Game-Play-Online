package pvsasoftware.mathgames;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;

import pvsasoftware.mathgames.Model.DataBaseHelper;
import pvsasoftware.mathgames.Model.HighScore;
import pvsasoftware.mathgames.Model.HighScoreComparetor;

/**
 * Created by thien on 12/12/2016.
 */

public class HighScoreActivity extends Activity {
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        ListView listView = (ListView) findViewById(R.id.li_highscore);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        HighScore highScore = new HighScore("Jonathan",84,"26-12-2016");
        ArrayList<HighScore> highScores = dataBaseHelper.getAllHighScore();
        highScores.add(highScore);
        Collections.sort(highScores,new HighScoreComparetor());
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(this, highScores);
        listView.setAdapter(highScoreAdapter);
        loadAdd();

    }
    private void loadAdd() {
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("90BBDFD007516E589EDD85245F36903B").build();
        adView.loadAd(adRequest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_full));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        interstitialAd.loadAd(adRequest);
    }


    @Override
    public void onBackPressed() {
        interstitialAd.show();
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }
}
