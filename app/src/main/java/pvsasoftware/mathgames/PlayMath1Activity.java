package pvsasoftware.mathgames;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import pvsasoftware.mathgames.Dialog.DialogListener;
import pvsasoftware.mathgames.Dialog.Dialog_endgame;
import pvsasoftware.mathgames.Dialog.Dialog_multiplayer_endgame;
import pvsasoftware.mathgames.Dialog.Dialog_startGame;
import pvsasoftware.mathgames.Fragment.Fragment_gameplay_1;
import pvsasoftware.mathgames.Fragment.Fragment_gameplay_2;
import pvsasoftware.mathgames.Fragment.Fragment_gameplay_3;
import pvsasoftware.mathgames.Model.DataBaseHelper;
import pvsasoftware.mathgames.Model.HighScore;
import pvsasoftware.mathgames.Multiplayer.PlayOnline.PlayOnlineHandler;



/**
 * Created by thien on 11/25/2016.
 */




public  class PlayMath1Activity extends Activity implements DialogListener.HighScore, PlayMath1MultiplayerInterface {

    private TextView tv_timer,tv_score,tv_numberQuestion,txtRoundPlay;
    private int timer;
    private Handler handler;
    private  Runnable runnable;
    private int numberQuestion = 1,scrore,roundPlay = 0;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmath_1);
        init();
        findViewBy();
        setLevel();
        showStartDialog();
        showFragmet_gameplay_1();
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("90BBDFD007516E589EDD85245F36903B").build();
        adView.loadAd(adRequest);
    }
    private void init() {
        numberQuestion = 1;
        scrore = 0;
        timer = 60;
    }
    private void setLevel() {

        MathGameApplication app = (MathGameApplication) getApplicationContext();
        SharedPreferences pre = app.getSharedPrefs();
        String level;
        int n = pre.getInt(OptionActivity.LEVEL,2);
        if (n == 1) {
            level = "EASY";
        }  else if (n == 2) {
            level = "MEDIUM";
        } else {
            level = "HARD";
        }
        TextView btLevel = (TextView) findViewById(R.id.tvLevel);
        if (MainHaindler.getInstance().getMultiplayer()) {
            btLevel.setVisibility(View.GONE);
        } else {
            btLevel.setVisibility(View.VISIBLE);
            btLevel.setText("Level:" + level);
        }


    }
    private void findViewBy() {
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_numberQuestion = (TextView)findViewById(R.id.tv_numberQuestion);
        txtRoundPlay = (TextView)findViewById(R.id.txtRoundPlay);
    }
    private void showStartDialog() {
        Dialog_startGame dialog_startGame = new Dialog_startGame(this);
        dialog_startGame.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                countdownTimer();
            }
        });
        dialog_startGame.show();
    }

    public void setRoundPlay() {
        roundPlay ++;
        String round = String.format(getString(R.string.roundPlay), roundPlay);
        txtRoundPlay.setText(round);
    }

    public void showFragmet_gameplay_1() {
        setRoundPlay();
        Fragment_gameplay_1 gameplay1 = new Fragment_gameplay_1();
        getFragmentManager().beginTransaction().replace(R.id.lngameplay, gameplay1).commit();
    }
    public void showFragment_gameplay_2() {
        setRoundPlay();
        Fragment_gameplay_2 gameplay2 = new Fragment_gameplay_2();
        getFragmentManager().beginTransaction().replace(R.id.lngameplay, gameplay2).commit();
    }
    public void showFragment_gameplay_3() {
        setRoundPlay();
        Fragment_gameplay_3 gameplay3 = new Fragment_gameplay_3();
        getFragmentManager().beginTransaction().replace(R.id.lngameplay, gameplay3).commit();
    }
    public void countdownTimer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                timer--;
                tv_timer.setText(timer + "");
                if (timer == 0) {
                    setEndGame();
                    return;
                }
                handler.postDelayed(this,1000);
            }
        };
        handler.post(runnable);
    }
    public void nextQuestion() {
        numberQuestion ++;
        tv_numberQuestion.setText( "Question: " + numberQuestion + " ");
    }
    public void setScore(){
        scrore ++;
        tv_score.setText( scrore + " ");
        playMusic(true,false);
    }
    public void reduceScore() {
        if (scrore > 4) {
            scrore = scrore -4;
        }
        tv_score.setText( scrore + " ");
        playMusic(false,false);
    }
    private void setEndGame() {
        if (MainHaindler.getInstance().getMultiplayer()) {
            final PlayOnlineHandler playOnlineHandler = new PlayOnlineHandler(this);
            playOnlineHandler.setScore(scrore);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playOnlineHandler.getScore();
                }
            },4000);

        } else {
            if (MainHaindler.getInstance().getFakeUser()) {
                Random r = new Random();
                int scorePlay = r.nextInt(4);
                int set = r.nextInt(2);
                if (set == 0) {
                    scorePlay = scorePlay + scrore;
                    setMultiplayerEndGame(scorePlay);
                } else {
                    if(scrore >= scorePlay) {
                        scorePlay = scrore - scorePlay;
                        setMultiplayerEndGame(scorePlay);
                    } else {
                        setMultiplayerEndGame(scorePlay);
                    }
                }
            } else {
                Dialog_endgame dialog_endgame = new Dialog_endgame(this);
                dialog_endgame.setDataForDialog(scrore,roundPlay,this);
                dialog_endgame.show();
            }


        }

    }
    public void setMultiplayerEndGame(int playerScore) {
        Dialog_multiplayer_endgame dialog_multiplayer_endgame = new Dialog_multiplayer_endgame(this);
        dialog_multiplayer_endgame.setDataForDialog(scrore,playerScore,this);
        dialog_multiplayer_endgame.show();

    }

    private void openHighScoreTable() {
        Intent intent = new Intent(this,HighScoreActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onSaveHighScore(String name) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(date);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        HighScore highScore = new HighScore(name,scrore,dateString);
        dataBaseHelper.createHighScore(highScore);
        openHighScoreTable();

    }

    @Override
    public void onCancelHighScore() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (Thread.currentThread().isAlive()) {
                Thread.currentThread().interrupt();
           handler.removeCallbacks(runnable);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }

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

    private void playMusic(Boolean n ,boolean isLoop){
        Musicplayer.getMusicPlayer().stop();
        if  (n == true) {
            Musicplayer.getMusicPlayer().setup(R.raw.correct);
        } else {
            Musicplayer.getMusicPlayer().setup(R.raw.wrong);
        }
        Musicplayer.getMusicPlayer().play(isLoop);
    }
}
