package com.weingoldeamon.quickclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int nav = 0;
    LeaderboardFragment lf;
    GameFragment gf;
    FragmentManager fm;
    Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lf = new LeaderboardFragment();
        gf = new GameFragment();
        fm = getSupportFragmentManager();
        navButton = findViewById(R.id.nav_button);
    }


    public void changeFragment(View view) {
        if(nav == 0) {
            nav = 1;
            navButton.setText("Back to Game");
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, lf);
            transaction.commit();
        }
        else {
            nav = 0;
            navButton.setText("View Leaderboard");
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, gf);
            transaction.commit();
        }
    }
}

/*
package com.weingoldeamon.quickclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    String[] scores = new String[10];
    ArrayList<Double> scoreData;
    ArrayAdapter scoreAdapter;
    ListView scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leaderboard);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scoreData = (ArrayList<Double>)(extras.get("scoreData"));
        }

        for(int i = 0; i < 10; i++)
            scores[i] = "-- seconds";
        Collections.sort(scoreData);
        int num = 10;
        if(scoreData.size() < num)
            num = scoreData.size();
        for(int i = 0; i < num; i++) {
            scores[i] = String.format("%.3f", scoreData.get(i)) + " seconds";
        }

        scoreAdapter = new ArrayAdapter<String>(this, R.layout.list_item, scores);
        scoreList = findViewById(R.id.score_list);
        scoreList.setAdapter(scoreAdapter);
    }

    public void backtoMain(View view) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    public void clearLeaderboard(View view) {
        for(int i = 0; i < 10; i++)
            scores[i] = "-- seconds";
        scoreAdapter.notifyDataSetChanged();
    }
}

 */