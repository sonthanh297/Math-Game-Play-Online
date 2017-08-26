package pvsasoftware.mathgames.Multiplayer.enties;


import java.util.List;

/**
 * Created by thien on 12/21/2016.
 */

public class GameTypeOnline1 {
    public  int firstNumber;
    public int secondNumber;
    public  int operation;
    public int trueAnswer;
    public List<Integer> answer;
     public GameTypeOnline1() {

     }

    public GameTypeOnline1(int firstNumber, int secondNumber, int operation, int trueAnswer,List<Integer> answer){
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
        this.trueAnswer = trueAnswer;
        this.answer = answer;
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
