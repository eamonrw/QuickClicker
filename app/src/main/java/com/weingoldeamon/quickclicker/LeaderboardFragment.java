package com.weingoldeamon.quickclicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {

    String[] scores = new String[10];
    ArrayList<Double> scoreData;
    ArrayAdapter scoreAdapter;
    ListView scoreList;
    Button clearButton;

    public LeaderboardFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scoreData = (ArrayList<Double>)(extras.get("scoreData"));
        }*/

        for(int i = 0; i < 10; i++)
            scores[i] = "-- seconds";

        /*Collections.sort(scoreData);
        int num = 10;
        if(scoreData.size() < num)
            num = scoreData.size();
        for(int i = 0; i < num; i++) {
            scores[i] = String.format("%.3f", scoreData.get(i)) + " seconds";
        }*/

        scoreAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, scores);
        scoreList = view.findViewById(R.id.score_list);
        scoreList.setAdapter(scoreAdapter);
        clearButton = view.findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < 10; i++)
                    scores[i] = "-- seconds";
                scoreAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
