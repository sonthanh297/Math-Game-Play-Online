package pvsasoftware.mathgames.Multiplayer.enties;

import java.util.List;

/**
 * Created by thien on 12/31/2016.
 */

public class GameTypeOnline2 {
    public  int firstNumber;
    public int secondNumber;
    public  int operation;
    public int trueAnswer;
    public List<Integer> answer;
    public boolean checkAnswer ;
    public int quetion_answer;
    public GameTypeOnline2() {

    }

    public GameTypeOnline2(int firstNumber, int secondNumber, int operation, int trueAnswer,boolean checkAnswer, int
             quetion_answer,List<Integer> answer){
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
        this.trueAnswer = trueAnswer;
        this.answer = answer;
        this.checkAnswer = checkAnswer;
        this.quetion_answer = quetion_answer;
    }

    public int getQuetion_answer() {
        return quetion_answer;
    }

    public boolean getCheckAnswer() {
        return checkAnswer;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getOperation() {
        return operation;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public int getTrueAnswer() {
        return trueAnswer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }
}
