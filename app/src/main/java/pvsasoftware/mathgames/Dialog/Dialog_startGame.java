package pvsasoftware.mathgames.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/2/2016.
 */

public class Dialog_startGame extends Dialog {
    private TextView tv_startGame;
    private CountDownTimer timer;
    private int settime = 4;
    private Handler handler;
    public Dialog_startGame(Context context) {
        super(context);
        Context mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_startgame);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setTimer();
    }
    private void setTimer(){
        handler = new Handler();
        tv_startGame = (TextView)findViewById(R.id.tv_dialog_startgame);
//        timer = new CountDownTimer(4500,1000) {
//            int count = 3;
//            @Override
//            public void onTick(long l) {
//
//                if (count == 0) {
//                    tv_startGame.setText("LET GO");
//                } else {
//                    tv_startGame.setText(count + "");
//                }
//                count --;
//
//            }
//            @Override
//            public void onFinish() {
//                dismiss();
//            }
//        };
//        timer.start();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                settime --;
                if (settime == 0) {
                    tv_startGame.setText( " LET GO");
                } else {
                    tv_startGame.setText(settime + "");
                }
                if ( settime <0 ) {
                    dismiss();
                    return;
                }
                handler.postDelayed(this,1000);

            }
        };
        handler.post(runnable);


    }



}
