package pvsasoftware.mathgames;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by thien on 1/2/2017.
 */

public class OptionActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String LEVEL = "Level";
    public SharedPreferences pre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
         init();
        MathGameApplication app = (MathGameApplication)getApplicationContext();
         pre = app.getSharedPrefs();

    }

    private void init() {
        Button btnEasy = (Button) findViewById(R.id.bt_easy);
        Button btnMedium = (Button) findViewById(R.id.bt_medium);
        Button btnHard = (Button) findViewById(R.id.bt_hard);

        btnEasy.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnHard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_easy:
                setEasy();
                break;
            case R.id.bt_medium:
                setMedium();
                break;
            case R.id.bt_hard:
                setHard();
                break;
        }
    }


    private void setEasy() {
        SharedPreferences.Editor editor= pre.edit();
        editor.putInt(LEVEL,1);
        editor.commit();
        onBackPressed();

    }
    private void setMedium() {
        SharedPreferences.Editor editor= pre.edit();
        editor.putInt(LEVEL,2);
        editor.commit();
        onBackPressed();

    }
    private void setHard() {
        SharedPreferences.Editor editor= pre.edit();
        editor.putInt(LEVEL,3);
        editor.commit();
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
