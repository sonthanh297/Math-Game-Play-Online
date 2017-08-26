package pvsasoftware.mathgames.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import pvsasoftware.mathgames.Model.DataManager;
import pvsasoftware.mathgames.Model.GameType1;
import pvsasoftware.mathgames.PlayMath1Activity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/1/2016.
 */

public class Fragment_gameplay_1 extends Fragment  implements View.OnClickListener{
    private TextView question,answer1,answer2,answer3,answer4,answer5,answer6;
    private View rootView;
    private DataManager dataManager;
    private ArrayList<GameType1> gameType1s;
    private GameType1 gameType1;
    private int numberQuestion = 0;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             rootView = inflater.inflate(R.layout.frament_game1,container,false);
            numberQuestion = 0;
            findViewIDs();
            getData();
            setData(0);
            setEvent();
            setAnimation();
            return rootView;
    }

    private void findViewIDs(){
        question = (TextView)rootView.findViewById(R.id.tv_question);
        answer1 = (TextView)rootView.findViewById(R.id.tv_answer1);
        answer2 = (TextView)rootView.findViewById(R.id.tv_answer2);
        answer3 = (TextView)rootView.findViewById(R.id.tv_answer3);
        answer4 = (TextView)rootView.findViewById(R.id.tv_answer4);
        answer5 = (TextView)rootView.findViewById(R.id.tv_answer5);
        answer6 = (TextView)rootView.findViewById(R.id.tv_answer6);

    }

    private void getData() {
        dataManager = new DataManager(getActivity());
        gameType1s = dataManager.createArrayGameType1(20);
    }
    private void setData(int n) {
        setEnableButton();
        gameType1 = gameType1s.get(n);
        int[] answer = gameType1.getAnswer();
        question.setText(gameType1.questionString());
        answer1.setBackgroundResource(R.drawable.answer_back);
        answer2.setBackgroundResource(R.drawable.answer_back);
        answer3.setBackgroundResource(R.drawable.answer_back);
        answer4.setBackgroundResource(R.drawable.answer_back);
        answer5.setBackgroundResource(R.drawable.answer_back);
        answer6.setBackgroundResource(R.drawable.answer_back);
        answer1.setText(answer[0]+ "");
        answer2.setText(answer[1]+ "");
        answer3.setText(answer[2]+ "");
        answer4.setText(answer[3] + "");
        answer5.setText(answer[4] + "");
        answer6.setText(answer[5] + "");
    }


   private void setEvent() {
       answer1.setOnClickListener(this);
       answer2.setOnClickListener(this);
       answer3.setOnClickListener(this);
       answer4.setOnClickListener(this);
       answer5.setOnClickListener(this);
       answer6.setOnClickListener(this);
   }

    private void setAnimation() {
        Animation animation_question = AnimationUtils.loadAnimation(getActivity(),R.anim.anim_question);
        Animation animation_answer = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
        animation_answer.setDuration(3000);
        question.setAnimation(animation_question);
        answer1.setAnimation(animation_answer);
        answer2.setAnimation(animation_answer);
        answer3.setAnimation(animation_answer);
        answer4.setAnimation(animation_answer);
        answer5.setAnimation(animation_answer);
        answer6.setAnimation(animation_answer);
    }
    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case  R.id.tv_answer1:
                checkTrueAnswer(0,view);
                setDisableButton();
                break;
            case R.id.tv_answer2:
                checkTrueAnswer(1,view);
                setDisableButton();
                break;
            case R.id.tv_answer3:
                checkTrueAnswer(2,view);
                setDisableButton();
                break;
            case R.id.tv_answer4:
                checkTrueAnswer(3,view);
                setDisableButton();
                break;
            case R.id.tv_answer5:
                checkTrueAnswer(4,view);
                setDisableButton();
                break;
            case R.id.tv_answer6:
                checkTrueAnswer(5,view);
                setDisableButton();
                break;
            default:
                break;
        }
        numberQuestion ++;
        final PlayMath1Activity activity = (PlayMath1Activity) getActivity();
        if (numberQuestion < 19) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setData(numberQuestion);
                    activity.nextQuestion();
                }
            },300);

        } else {
                    activity.nextQuestion();
                    activity.showFragment_gameplay_2();

        }


    }

    private void setEnableButton() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
         answer5.setEnabled(true);
        answer6.setEnabled(true);
    }
    private void setDisableButton() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
        answer5.setEnabled(false);
        answer6.setEnabled(false);
    }
    private void checkTrueAnswer(int number,View view) {
        PlayMath1Activity activity = (PlayMath1Activity) getActivity();
        if (gameType1s.get(numberQuestion).getTrueAnswer() == gameType1s.get(numberQuestion).getAnswer()[number]) {
            view.setBackgroundResource(R.drawable.correct_back);

            activity.setScore();
        } else {
            activity.reduceScore();
            view.setBackgroundResource(R.drawable.incorrect_back);
        }

    }
}
