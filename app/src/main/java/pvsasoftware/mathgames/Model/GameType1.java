package pvsasoftware.mathgames.Model;

/**
 * Created by thien on 12/10/2016.
 */

public class GameType1 {
        private int firstNumber;
        private int secondNumber;
        private int operation;
        private int trueAnswer;
        private int[] answer = new int[6];

        public GameType1(int firstNumber,int secondNumber,int operation,int trueAnswer, int[] answer) {
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

    public int[] getAnswer() {
        return answer;
    }
    public String questionString() {
        String opeationString;
        if (operation == 0) {
            opeationString = " + ";
        } else {
            opeationString = " - ";
        }
        String question = firstNumber + opeationString + secondNumber + " = ?";
        return question;
    }
}
