package com.cnit355.myles.a425project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class LeaderboardArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> users, scores;
    //private final List<Score> score;

    public LeaderboardArrayAdapter(@NonNull Context c, List<String> u, List<String> s) {
        super(c, R.layout.custom_leaderboard_layout);
        context = c;
        users = u;
        scores = s;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_leaderboard_layout, parent, false);
        TextView userTextView = rowView.findViewById(R.id.user);
        TextView scoreTextView = rowView.findViewById(R.id.score);
        userTextView.setText(users.get(position));
        scoreTextView.setText(scores.get(position));

        return rowView;
    }
}
