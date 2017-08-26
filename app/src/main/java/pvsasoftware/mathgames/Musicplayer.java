package pvsasoftware.mathgames;

/**
 * Created by PhiSon on 5/10/2017.
 */

import android.media.MediaPlayer;

/**
 * Created by thien on 3/24/2017.
 */

public class Musicplayer {

    public static final int PLAYER_IDLE=-1;
    public static final int PLAYER_PLAY=1;
    public static final int PLAYER_PAUSE=2;
    public static final int PLAYER_STOP =3;
    private MediaPlayer mediaPlayer;
    private int state;
    private static Musicplayer instance;

    public static Musicplayer getMusicPlayer(){
        if (instance==null)
            instance=new Musicplayer();
        return instance;
    }

    public Musicplayer() {
        state=PLAYER_IDLE;
    }

    public void setup(int n) {
        mediaPlayer = MediaPlayer.create(MathGameApplication.getContext(),n);
//        try {
//            state=PLAYER_IDLE;
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(path);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void play(boolean n){
        if(state==PLAYER_IDLE){
            mediaPlayer.start();
            mediaPlayer.setLooping(n);
            state=PLAYER_PLAY;
        }
    }
    public void stop(){
        if(state==PLAYER_PLAY || state==PLAYER_PAUSE) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            state=PLAYER_IDLE;
        }
    }
    public void pause(){
        if(state==PLAYER_PLAY ) {
            mediaPlayer.pause();
            state=PLAYER_PAUSE;
        }
    }

    public int getState() {
        return state;
    }

    public void resume(){
        if(state==PLAYER_PAUSE) {
            mediaPlayer.start();
            state=PLAYER_PLAY;
        }
    }

    public void setFinish(final int n){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
                setup(n);
                play(true);
            }
        });
    }










}
