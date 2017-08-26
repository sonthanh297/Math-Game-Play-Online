package pvsasoftware.mathgames.Model;

/**
 * Created by thien on 12/11/2016.
 */

public class GameType2 extends GameType1 {
        private boolean checkAnswer ;
        private int quetion_answer;
    public GameType2(int firstNumber, int secondNumber, int operation, int trueAnswer, int[] answer,boolean checkAnswer, int quetion_answer) {
        super(firstNumber, secondNumber, operation, trueAnswer, answer);
        this.checkAnswer = checkAnswer;
        this.quetion_answer = quetion_answer;
    }

    @Override
    public int getFirstNumber() {
        return super.getFirstNumber();
    }

    @Override
    public int getOperation() {
        return super.getOperation();
    }

    @Override
    public int getSecondNumber() {
        return super.getSecondNumber();
    }

    @Override
    public int getTrueAnswer() {
        return super.getTrueAnswer();
    }

    @Override
    public int[] getAnswer() {
        return super.getAnswer();
    }

    public int getQuetion_answer() {
        return quetion_answer;
    }

    public boolean isCheckAnswer() {
        return checkAnswer;
    }

    public String questionStringType2() {
            String operationString;
            if (getOperation() == 0) {
                operationString = " + ";
            } else {
                operationString = " - ";
            }
            String question = getFirstNumber() + operationString + getSecondNumber() + " = " + quetion_answer;
        return question;
    }
}
