package com.weingoldeamon.quickclicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardFragment extends Fragment {

    String[] scores = new String[10];
    ArrayList<Double> scoreData;
    ArrayAdapter scoreAdapter;
    ListView scoreList;
    Button clearButton;
    RequestQueue requestQueue;
    String url = "https://proficiency-lab.sites.tjhsst.edu/get-leaderboard";
    String clear_url = "https://proficiency-lab.sites.tjhsst.edu/clear-leaderboard";

    public LeaderboardFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        for(int i = 0; i < 10; i++)
            scores[i] = "-- seconds";

        requestQueue = RequestQueueSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<Double> scr = jsonToList(response);
                    Collections.sort(scr);
                    int num = 10;
                    if(scr.size() < num)
                        num = scr.size();
                    for(int i = 0; i < 10; i++)
                        scores[i] = "-- seconds";
                    for(int i = 0; i < num; i++)
                        scores[i] = String.format("%.5f", scr.get(i)) + " seconds";

                    scoreAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    scores[0] = "Error";
                    scoreAdapter.notifyDataSetChanged();
                    error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);

        scoreAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, scores);
        scoreList = view.findViewById(R.id.score_list);
        scoreList.setAdapter(scoreAdapter);
        clearButton = view.findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, clear_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                        }
                });
                requestQueue.add(stringRequest);
                for(int i = 0; i < 10; i++)
                    scores[i] = "-- seconds";
                scoreAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public ArrayList<Double> jsonToList(String j) {
        ArrayList<Double> list = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(j);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                list.add(Double.parseDouble(jObject.getString("time")));
            }
        }
        catch(JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
        }
        return list;
    }
}
