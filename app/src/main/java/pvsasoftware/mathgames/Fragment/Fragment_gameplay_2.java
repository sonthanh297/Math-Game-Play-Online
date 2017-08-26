package pvsasoftware.mathgames.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pvsasoftware.mathgames.Model.DataManager;
import pvsasoftware.mathgames.Model.GameType2;
import pvsasoftware.mathgames.PlayMath1Activity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/11/2016.
 */

public class Fragment_gameplay_2 extends Fragment implements View.OnClickListener {
        private TextView tv_question;
        private Button btn_true,btn_fasle;
        private GameType2 gameType2;
        private ArrayList<GameType2> gameType2s;
        private DataManager dataManager;
        private View rootView;
        private int numberQuestionGame2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             rootView = inflater.inflate(R.layout.fragment_game2,container,false);
            numberQuestionGame2 = 0;
            findViewBy();
            setEvent();
            setAnimation();
            getData();
            setData(0);
            return rootView;
    }


    private void setAnimation() {
        Animation animation_question = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_down);
        Animation animation_answer = AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in);
        tv_question.setAnimation(animation_question);
        btn_true.setAnimation(animation_answer);
        btn_fasle.setAnimation(animation_answer);
    }
    private void findViewBy() {
            tv_question = (TextView) rootView.findViewById(R.id.tv_question2);
            btn_true = (Button) rootView.findViewById(R.id.btn_true);
            btn_fasle = (Button) rootView.findViewById(R.id.btn_false);
    }
    private void setEvent() {
        btn_true.setOnClickListener(this);
        btn_fasle.setOnClickListener(this);

    }
    private void getData() {
        dataManager = new DataManager(getActivity());
        gameType2s = dataManager.createArrayGameType2Duplicate(15);
    }
    private void setData( int number) {
        enableButton();
        gameType2 = gameType2s.get(number);
        tv_question.setText(gameType2.questionStringType2());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_true:
                checkTrueAnswer(true);
                disableButton();
                break;
            case R.id.btn_false:
                checkTrueAnswer(false);
                disableButton();
                break;
            default:
                break;
        }

        numberQuestionGame2++;
        final PlayMath1Activity activity = (PlayMath1Activity) getActivity();
        if (numberQuestionGame2 < 14 ){
                    setData(numberQuestionGame2);
                    activity.nextQuestion();

        } else {
                    activity.nextQuestion();
                    activity.showFragment_gameplay_3();

        }
    }

    private void enableButton() {
        btn_true.setEnabled(true);
        btn_fasle.setEnabled(true);
    }
    private void disableButton() {
        btn_true.setEnabled(false);
        btn_fasle.setEnabled(false);
    }




    private void checkTrueAnswer(boolean isTrue) {
        PlayMath1Activity activity = (PlayMath1Activity) getActivity();
            if (isTrue == gameType2s.get(numberQuestionGame2).isCheckAnswer()) {
                activity.setScore();
            } else {
                activity.reduceScore();
            }
    }
}
