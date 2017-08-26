package pvsasoftware.mathgames.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/12/2016.
 */

public class Dialog_endgame extends Dialog  implements View.OnClickListener {
    private Context mContext;
    private TextView tv_content,tv_round;
    private EditText et_name;
    private DialogListener.HighScore dialogListener;
    public Dialog_endgame(Context context) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_endgame);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        tv_content = (TextView)findViewById(R.id.tv_content);
        tv_round = (TextView)findViewById(R.id.txtEndRound);
        et_name = (EditText) findViewById(R.id.et_name);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        Button btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    public void setDataForDialog( int  content,int round,DialogListener.HighScore dialogListener) {
        tv_content.setText("Score: "+content);
        tv_round.setText(String.format(mContext.getString(R.string.compleRound),round));
        this.dialogListener = dialogListener;
    }


    @Override
    public void onClick(View view) {
        switch ( view.getId()) {
            case R.id.btn_cancel:
                dialogListener.onCancelHighScore();
                dismiss();
                break;
            case R.id.btn_save:
                dialogListener.onSaveHighScore(et_name.getText().toString());
                dismiss();
                break;
        }

    }
}
