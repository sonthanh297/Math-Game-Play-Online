package pvsasoftware.mathgames.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by thien on 12/12/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper  {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HighScore";
    private static final String TABLE_HIGHSCORE = "highscore";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SCORE = "score";
    private static final String DATE = "date";


    private static final String CREATE_TABLE_HIGHSCORE = "create table " + TABLE_HIGHSCORE + " (" + ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME +
            " char, " + SCORE + " integer, " + DATE + " char) ";



    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_HIGHSCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
            onCreate(sqLiteDatabase);

    }
        public long createHighScore(HighScore highScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME,highScore.getHighname());
        contentValues.put(SCORE,highScore.getHighscore());
        contentValues.put(DATE,highScore.getHighdate());
        return  db.insert(TABLE_HIGHSCORE,null,contentValues);
    }

    public ArrayList<HighScore> getAllHighScore() {
        ArrayList<HighScore> highScores = new ArrayList<>();
        String selectQuery ="SELECT  * FROM " + TABLE_HIGHSCORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                String name =  cursor.getString(cursor.getColumnIndex(NAME));
                int score = cursor.getInt(cursor.getColumnIndex(SCORE));
                String date = cursor.getString(cursor.getColumnIndex(DATE));
                highScores.add(new HighScore(name,score,date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return highScores;
    }
}
