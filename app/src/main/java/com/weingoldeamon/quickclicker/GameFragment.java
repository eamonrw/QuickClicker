package com.weingoldeamon.quickclicker;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.util.HashMap;

public class GameFragment extends Fragment {

    int status = 0;
    long cur_time;
    TextView infoText;
    CountDownTimer waitTimer;
    RequestQueue requestQueue;
    String url = "https://proficiency-lab.sites.tjhsst.edu/post-leaderboard";

    public GameFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        infoText = view.findViewById(R.id.info_text);
        requestQueue = RequestQueueSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View screen) {
                if(status == 0) {
                    screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorWait, null));
                    infoText.setText("Tap when the screen turns green.");
                    cur_time = System.nanoTime();
                    waitTimer = new CountDownTimer((long)(Math.random() * 6000 + 3000), 1000) {
                        public void onFinish() {
                            screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorGo, null));
                            infoText.setText("Tap now!");
                            status = 2;
                            cur_time = System.nanoTime();
                        }

                        public void onTick(long millisUntilFinished) {
                        }
                    }.start();
                    status = 1;
                }

                else if(status == 1) {
                    screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorStart, null));
                    infoText.setText("Don't tap before the screen turns green! Tap again to replay.");
                    status = 0;
                    waitTimer.cancel();
                }

                else {
                    double reactionTime = (System.nanoTime() - cur_time)/1000000000.0;
                    screen.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorStart, null));
                    infoText.setText("You tapped in " + String.format("%.5f", reactionTime) + " seconds. Tap again to replay.");
                    status = 0;

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("data", String.format("%.5f", reactionTime));
                    JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("Success");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    requestQueue.add(jreq);
                }
            }
        });

        return view;
    }
}