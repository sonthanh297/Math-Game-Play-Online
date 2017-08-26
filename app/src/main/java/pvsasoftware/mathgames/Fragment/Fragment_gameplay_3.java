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

import java.util.List;

import pvsasoftware.mathgames.Model.DataManager;
import pvsasoftware.mathgames.Model.GameType3;
import pvsasoftware.mathgames.PlayMath1Activity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 1/1/2017.
 */

public class Fragment_gameplay_3 extends Fragment implements View.OnClickListener {
    private TextView question,answer1,answer2,answer3,answer4;
    private View rootView;
    private DataManager dataManager;
    private List<GameType3> gameType3s;
    private GameType3 gameType3;
    private int numberQuestion = 0;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_game3,container,false);
        numberQuestion = 0;
        findViewIDs();
        getData();
       setData(0);
        setEvent();
        setAnimation();
        return rootView;
    }

    private void findViewIDs(){
        question = (TextView)rootView.findViewById(R.id.tv_question_type3);
        answer1 = (TextView)rootView.findViewById(R.id.tv_answer1_type3);
        answer2 = (TextView)rootView.findViewById(R.id.tv_answer2_type3);
        answer3 = (TextView)rootView.findViewById(R.id.tv_answer3_type3);
        answer4 = (TextView)rootView.findViewById(R.id.tv_answer4_type3);

    }

    private void getData() {
        dataManager = new DataManager(getActivity());
        gameType3s = dataManager.getGameType3list();
    }
    private void setData(int n) {
        setEnableButton();
        gameType3 = gameType3s.get(n);
        List<String> answer = gameType3.getAnswerList();
        question.setText("" + gameType3.getTrueAnswer());
        answer1.setBackgroundResource(R.drawable.answer_back);
        answer2.setBackgroundResource(R.drawable.answer_back);
        answer3.setBackgroundResource(R.drawable.answer_back);
        answer4.setBackgroundResource(R.drawable.answer_back);

        answer1.setText(answer.get(0)+ "");
        answer2.setText(answer.get(1)+ "");
        answer3.setText(answer.get(2)+ "");
        answer4.setText(answer.get(3) + "");

    }


    private void setEvent() {
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
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

    }
    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case  R.id.tv_answer1_type3:
                checkTrueAnswer(0,view);
                setDisableButton();
                break;
            case R.id.tv_answer2_type3:
                checkTrueAnswer(1,view);
                setDisableButton();
                break;
            case R.id.tv_answer3_type3:
                checkTrueAnswer(2,view);
                setDisableButton();
                break;
            case R.id.tv_answer4_type3:
                checkTrueAnswer(3,view);
                setDisableButton();
                break;
            default:
                break;
        }
        numberQuestion ++;
        final PlayMath1Activity activity = (PlayMath1Activity) getActivity();
        if (numberQuestion < 14) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setData(numberQuestion);
                    activity.nextQuestion();
                }
            },300);

        } else {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.nextQuestion();
                    activity.showFragmet_gameplay_1();
                }
            },300);

        }
    }

    private void setEnableButton() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
    }
    private void setDisableButton() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
    }


    private void checkTrueAnswer(int number,View view) {
        PlayMath1Activity activity = (PlayMath1Activity) getActivity();
        if (gameType3s.get(numberQuestion).getCorrectIndex() == number) {
            view.setBackgroundResource(R.drawable.correct_back);

            activity.setScore();
        } else {
            activity.reduceScore();
            view.setBackgroundResource(R.drawable.incorrect_back);
        }

    }
}
