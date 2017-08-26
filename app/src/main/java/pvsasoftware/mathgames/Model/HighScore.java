package pvsasoftware.mathgames.Model;


/**
 * Created by thien on 12/12/2016.
 */

public class HighScore {
    private String highname;
    private int highscore;
    private String highdate;

    public HighScore(String highname,int highscore,String highdate) {
        this.highname = highname;
        this.highscore = highscore;
        this.highdate = highdate;
    }


    public String getHighdate() {
        return highdate;
    }

    public int getHighscore() {
        return highscore;
    }

    public String getHighname() {
        return highname;
    }

    public void setHighdate(String highdate) {
        this.highdate = highdate;
    }

    public void setHighname(String highname) {
        this.highname = highname;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
