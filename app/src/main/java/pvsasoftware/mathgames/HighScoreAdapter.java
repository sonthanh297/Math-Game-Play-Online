package pvsasoftware.mathgames;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pvsasoftware.mathgames.Model.HighScore;

/**
 * Created by thien on 12/13/2016.
 */

public class HighScoreAdapter extends BaseAdapter {
    private ArrayList<HighScore> highScores;
    private Context context;
    public HighScoreAdapter( Context context,ArrayList<HighScore> highScores) {
        this.highScores = highScores;
        this.context = context;
    }


    @Override
    public int getCount() {
        return highScores == null ? 0 : highScores.size();
    }

    @Override
    public HighScore getItem(int i) {
        return highScores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder {
        TextView tv_highname,tv_highScore,tv_highDate;
                ImageView img_name;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.highscore_view,parent,false);
            holder.img_name = (ImageView) convertView.findViewById(R.id.img_name);
            holder.tv_highname = (TextView) convertView.findViewById(R.id.tv_highname);
            holder.tv_highScore = (TextView) convertView.findViewById(R.id.tv_highscore);
            holder.tv_highDate = (TextView) convertView.findViewById(R.id.tv_highdate);
            holder.tv_highScore.setText(getItem(i).getHighscore()+"");
            holder.tv_highDate.setText(getItem(i).getHighdate());
            holder.tv_highname.setText(getItem(i).getHighname());
        if ( i == 0) {
            holder.img_name.setImageResource(R.drawable.king);
            convertView.setBackgroundColor(Color.parseColor("#9C27B0"));
            holder.tv_highname.setTextColor(Color.parseColor("#FF9800"));
        } else if ( i == 1) {
            holder.img_name.setImageResource(R.drawable.star);
            convertView.setBackgroundColor(Color.parseColor("#009688"));
            holder.tv_highname.setTextColor(Color.parseColor("#00E676"));
        }  else {
            holder.img_name.setImageResource(R.drawable.star);
        }

        return convertView;
    }
}
