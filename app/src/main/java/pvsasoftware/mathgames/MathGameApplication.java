package pvsasoftware.mathgames;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thien on 1/2/2017.
 */

public class MathGameApplication extends Application {
    private SharedPreferences preferences;

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }


    public SharedPreferences getSharedPrefs() {
        if (preferences == null) {
            preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        }
        return preferences;

    }

    public  static Context getContext() {
        return context;
    }
}
