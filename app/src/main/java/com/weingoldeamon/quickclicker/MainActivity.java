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