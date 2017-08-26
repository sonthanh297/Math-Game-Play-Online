package pvsasoftware.mathgames.Model;

import java.util.List;

/**
 * Created by thien on 1/1/2017.
 */

public class GameType3 {

     int trueAnswer;
    int correctIndex;
     List<String> answerList;
    public GameType3(){

    }
    public GameType3(int trueAnswer,int correctIndex,List<String> answerList ) {
        this.trueAnswer = trueAnswer;
        this.answerList = answerList;
        this.correctIndex = correctIndex;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public int getTrueAnswer() {
        return trueAnswer;
    }

    public List<String> getAnswerList() {
        return answerList;
    }
}
