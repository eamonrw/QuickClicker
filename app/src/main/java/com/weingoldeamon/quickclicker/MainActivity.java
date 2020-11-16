package com.weingoldeamon.quickclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int status = 0;
    TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoText = findViewById(R.id.info_text);

    }

    public void screenTap(View screen) {
        if(status == 0) {
            screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorWait, null));
            infoText.setText("Tap when the screen turns green.");
            status = 1;
        }

        else if(status == 1) {
            screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorGo, null));
            infoText.setText("Tap now!");
            status = 2;
        }

        else {
            screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorStart, null));
            infoText.setText("You tapped in 0 seconds. Tap again to replay.");
            status = 0;
        }

    }
}
