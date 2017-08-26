package pvsasoftware.mathgames.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.MathGameApplication;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline1;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline2;
import static pvsasoftware.mathgames.OptionActivity.LEVEL;

/**
 * Created by thien on 12/10/2016.
 */

public class DataManager   {
        private ArrayList<GameType1> gameType1s ;
        private GameType1 gameType1;
        private SharedPreferences pre;
        private Context mContext;
        public DataManager(Context context) {
                this.mContext = context;
        }
        private int createRandomNumber(int n) {
                Random r = new Random();
                return r.nextInt(n);
        }
        private GameType1 createGametype1() {
                MathGameApplication app = (MathGameApplication) mContext.getApplicationContext();
                pre = app.getSharedPrefs();
                int level;
                int n = pre.getInt(LEVEL,2);
                if (n == 1) {
                      level = 20;
                }  else if (n == 2) {
                        level = 50;
                } else {
                        level = 100;
                }


                int firstNumber = createRandomNumber(level);
                int seconNumber = createRandomNumber(level);
                int operation = createRandomNumber(2);
                int trueAnswer;
                int answer[] = new int[6];
                if (operation == 0) {
                        trueAnswer = firstNumber + seconNumber;
                } else {
                        if (firstNumber >= seconNumber) {
                                trueAnswer = firstNumber - seconNumber;
                        } else {
                                int temp;
                                temp = firstNumber;
                                firstNumber = seconNumber;
                                seconNumber = temp;
                                trueAnswer = firstNumber - seconNumber;
                        }

                }
                int correctIndex = createRandomNumber(6);
                answer[correctIndex] = trueAnswer;
                for (int i = 0; i<6;i++ ) {
                        if (i == correctIndex) {
                                continue;
                        }
                        int multiplier = 1 + createRandomNumber(10);
                        if (Math.random() > 0.5) {
                                answer[i] = trueAnswer + multiplier * (i +1 );
                        } else  {
                                int numberAnswer = trueAnswer - multiplier * ( i +1) ;
                                if (numberAnswer < 0) {
                                        numberAnswer = trueAnswer + multiplier * ( i + 5);
                                }
                                answer[i] = numberAnswer;
                        }
                }
                gameType1 = new GameType1(firstNumber,seconNumber,operation,trueAnswer,answer);
                return  gameType1;
        }
        public  ArrayList<GameType1> createArrayGameType1 (int number) {
                gameType1s = new ArrayList<>();
                if (MainHaindler.getInstance().getMultiplayer()){
                     List<GameTypeOnline1> gameTypeOnline1s = MainHaindler.getInstance().getGameTypeOnline1s();
                        for (GameTypeOnline1 gameTypeOnline1 : gameTypeOnline1s) {
                                int[] answer = new int[6];
                                for (int i = 0; i< gameTypeOnline1.getAnswer().size();i++){
                                        answer[i] = gameTypeOnline1.getAnswer().get(i);
                                }
                                gameType1s.add(new GameType1(gameTypeOnline1.getFirstNumber(),gameTypeOnline1.getSecondNumber(),gameTypeOnline1.getOperation(),gameTypeOnline1.getTrueAnswer(),answer));
                        }
                        return gameType1s;

                } else {

                        gameType1s.add(createGametype1());
                        for (int i = 1; i < number; i++) {
                                boolean duplicate = false;
                                GameType1 type;
                                do {
                                        type = createGametype1();
                                        for (GameType1 gametype : gameType1s) {
                                                if (gametype.questionString().equalsIgnoreCase(type.questionString())) {
                                                        duplicate = true;
                                                        break;
                                                }
                                        }
                                }
                                while (duplicate );
                                gameType1s.add(type);
                        }

                        return gameType1s;
                }
        }
        public ArrayList<GameType1> creatArrayGameType1Duplicate ( int number) {
                gameType1s = new ArrayList<>();
                for (int i = 0 ; i < number; i++) {
                        gameType1s.add(createGametype1());
                }
                return gameType1s;
        }
        private GameType2 createGametype2() {
                gameType1 = createGametype1();
                boolean checkAnswer ;
                int question_answer = 0;
                if (Math.random() > 0.5) {
                        checkAnswer = true;
                        question_answer = gameType1.getTrueAnswer();
                } else {
                        checkAnswer = false;
                        for (int i = 0; i< gameType1.getAnswer().length;i++){
                                if (gameType1.getAnswer()[i] == gameType1.getTrueAnswer()){
                                        continue;
                                } else {
                                        question_answer = gameType1.getAnswer()[i];
                                        break;
                                }
                        }
                }
                GameType2 gameType2 = new GameType2(gameType1.getFirstNumber(), gameType1.getSecondNumber(), gameType1.getOperation(), gameType1.getTrueAnswer(), gameType1.getAnswer(), checkAnswer, question_answer);
                return gameType2;
        }
        public ArrayList<GameType2> createArrayGameType2Duplicate ( int number) {
                ArrayList<GameType2> gameType2s = new ArrayList<>();

                if (MainHaindler.getInstance().getMultiplayer()){
                        List<GameTypeOnline2> gameTypeOnline2s = MainHaindler.getInstance().getGameTypeOnline2s();
                        for (GameTypeOnline2 gameTypeOnline2 : gameTypeOnline2s) {
                                int[] answer = new int[6];
                                for (int i = 0; i< gameTypeOnline2.getAnswer().size();i++){
                                        answer[i] = gameTypeOnline2.getAnswer().get(i);
                                }
                                gameType2s.add(new GameType2(gameTypeOnline2.getFirstNumber(),gameTypeOnline2.getSecondNumber(),gameTypeOnline2.getOperation(),gameTypeOnline2.getTrueAnswer(),answer,gameTypeOnline2.getCheckAnswer(),gameTypeOnline2.getQuetion_answer()));
                        }
                        return gameType2s;

                } else {
                        for (int i = 0; i< number; i++) {
                                gameType2s.add(createGametype2());
                        }
                        return gameType2s;
                }


        }

        public List<GameTypeOnline1> creatGameTypeOnline1() {
                List<GameTypeOnline1> gameTypeOnline1s = new ArrayList<>();
                ArrayList<GameType1> gameType1s = creatArrayGameType1Duplicate(20);
               for (GameType1  gameType1 : gameType1s){
                       List<Integer> intList = new ArrayList<>();
                       for (int i : gameType1.getAnswer()){
                               intList.add(i);
                       }
                       GameTypeOnline1 gameTypeOnline1 = new GameTypeOnline1(gameType1.getFirstNumber(),gameType1.getSecondNumber(),gameType1.getOperation(),gameType1.getTrueAnswer(),intList);
                       gameTypeOnline1s.add(gameTypeOnline1);

               }

                return gameTypeOnline1s;
        }
        public List<GameTypeOnline2> creatGameTypeOnline2() {
                List<GameTypeOnline2> gameTypeOnline2s = new ArrayList<>();
                ArrayList<GameType2> gameType2s = createArrayGameType2Duplicate(15);
                for (GameType2  gameType2 : gameType2s){
                        List<Integer> intList = new ArrayList<>();
                        for (int i : gameType2.getAnswer()){
                                intList.add(i);
                        }
                        GameTypeOnline2 gameTypeOnline2 = new GameTypeOnline2(gameType2.getFirstNumber(),gameType2.getSecondNumber(),gameType2.getOperation(),gameType2.getTrueAnswer(),gameType2.isCheckAnswer(),gameType2.getQuetion_answer(),intList);
                        gameTypeOnline2s.add(gameTypeOnline2);

                }

                return gameTypeOnline2s;
        }


        private GameType3 creatGameType3() {
                MathGameApplication app = (MathGameApplication) mContext.getApplicationContext();
                pre = app.getSharedPrefs();
                int level;
                int n = pre.getInt(LEVEL,2);
                if (n == 1) {
                        level = 20;
                }  else if (n == 2) {
                        level = 50;
                } else {
                        level = 100;
                }
                int firstNumber = createRandomNumber(level);
                int seconNumber = createRandomNumber(level);
                int operation = createRandomNumber(2);
                int trueAnswer;
                String correctString;
                List<String> answer = new ArrayList<>();
                if (operation == 0) {
                        trueAnswer = firstNumber + seconNumber;
                } else {
                        if (firstNumber >= seconNumber) {
                                trueAnswer = firstNumber - seconNumber;
                        } else {
                                int temp;
                                temp = firstNumber;
                                firstNumber = seconNumber;
                                seconNumber = temp;
                                trueAnswer = firstNumber - seconNumber;
                        }

                }
                int correctIndex = createRandomNumber(4);
                if (operation == 0) {
                        correctString = "" + firstNumber + " + " + seconNumber + " ";
                } else {
                        correctString = "" + firstNumber + " - " + seconNumber + " ";
                }


                for (int i = 0 ; i<4 ; i++) {
                        int tempfirstNumber;
                        int tempseconNumber;
                        int tempoperation;
                        int temptrueAnswer;
                        String tempcorrectString;
                        Boolean check ;
                        if (i == correctIndex) {
                                answer.add(correctString);
                        } else {

                                do {
                                        check = false;
                                        tempfirstNumber = createRandomNumber(level);
                                        tempseconNumber = createRandomNumber(level);
                                        tempoperation = createRandomNumber(2);
                                        if (tempoperation == 0) {
                                                temptrueAnswer = tempfirstNumber + tempseconNumber;
                                        } else {
                                                temptrueAnswer = tempfirstNumber - tempseconNumber;
                                        }
                                        if (temptrueAnswer == trueAnswer) {
                                                check = true;
                                        }
                                } while (check );
                                if (tempoperation == 0) {
                                        tempcorrectString = "" + tempfirstNumber + " + " + tempseconNumber + " ";
                                } else {
                                        tempcorrectString = "" + tempfirstNumber + " - " + tempseconNumber + " ";
                                }

                                answer.add(tempcorrectString);

                        }
                }
                return new GameType3(trueAnswer,correctIndex,answer);

        }


        public List<GameType3> createGameType3list() {
                List<GameType3> gameType3s = new ArrayList<>();
                for (int i = 0;i <15; i++) {
                        gameType3s.add(creatGameType3());
                }
                return gameType3s;
        }


        public List<GameType3> getGameType3list()  {
                if (MainHaindler.getInstance().getMultiplayer()){
                        return MainHaindler.getInstance().getGameType3s();
                } else {
                        List<GameType3> gameType3s;
                        gameType3s = createGameType3list();
                        return gameType3s;
                }
        }









}
