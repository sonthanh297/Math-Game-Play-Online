package pvsasoftware.mathgames.Model;

import java.util.Comparator;

/**
 * Created by thien on 12/12/2016.
 */

public class HighScoreComparetor implements Comparator<HighScore> {
    @Override
    public int compare(HighScore h1, HighScore h2) {
        return h1.getHighscore() < h2.getHighscore() ? 1:-1;
    }
}
