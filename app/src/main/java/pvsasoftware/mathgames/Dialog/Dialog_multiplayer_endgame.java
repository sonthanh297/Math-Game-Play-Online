package pvsasoftware.mathgames.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import pvsasoftware.mathgames.R;

/**
 * Created by thien on 1/2/2017.
 */

public class Dialog_multiplayer_endgame extends Dialog implements View.OnClickListener {

    private Context mContext;
    private TextView mtv_complete,mtv_playerScore,mtv_yourScore;
    private Button btn_cancel;
    private DialogListener.HighScore dialogListener;
    public Dialog_multiplayer_endgame(Context context) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_multiplayer_endgame);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        mtv_complete = (TextView)findViewById(R.id.mtv_complete);
        mtv_yourScore = (TextView)findViewById(R.id.mtv_yourScore);
        mtv_playerScore = (TextView) findViewById(R.id.mtv_playerScore);
        btn_cancel = (Button)findViewById(R.id.mtv_cancel);
        btn_cancel.setOnClickListener(this);
    }

    public void setDataForDialog( int  yourScore,int playerScore,DialogListener.HighScore dialogListener) {
        this.dialogListener = dialogListener;
        if (yourScore > playerScore) {
            mtv_complete.setText(mContext.getString(R.string.win));
        } else {
            mtv_complete.setText(mContext.getString(R.string.lose));
        }
        mtv_yourScore.setText(" " + yourScore);
        mtv_playerScore.setText(" "+ playerScore);

    }


    @Override
    public void onClick(View view) {
        switch ( view.getId()) {
            case R.id.mtv_cancel:
                dialogListener.onCancelHighScore();
                dismiss();
                break;
        }

    }




}
