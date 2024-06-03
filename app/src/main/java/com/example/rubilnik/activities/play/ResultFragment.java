package com.example.rubilnik.activities.play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ResultFragment extends Fragment {

    public static Button btnReturn;
    JSONArray scores;
    public ResultFragment(JSONArray scores) {
        this.scores = scores;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);
        LinearLayout leaderBoardLinLay = rootView.findViewById(R.id.leaderBoardLinLay);
        btnReturn = rootView.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> {
            requireActivity().finish();
        });


        for (int i=0; i<scores.length();i++){
            String id="";
            // if ( id.equals(QuizActivity.getUserId())) {} //!
            String name="";
            Integer points=null;
            try {
                JSONObject score = scores.getJSONObject(i);
                id = score.getString("userId");
                points = score.getInt("userScore");
            } catch (JSONException e) {MyTools.LogError(e);}
            TextView placeTextV = new TextView(this.getContext());
            placeTextV.setText(String.valueOf(i));
            TextView idTextV = new TextView(this.getContext());
            idTextV.setText(id);
            TextView nameTextV = new TextView(this.getContext());

            nameTextV.setText(name);
            TextView pointsTextV = new TextView(this.getContext());
            if (points!=null) {pointsTextV.setText(points.toString());}

            LinearLayout scoreLog = new LinearLayout(this.getContext());
            View v = new View(this.getContext());

            scoreLog.addView(placeTextV);
            scoreLog.addView(idTextV);
            scoreLog.addView(nameTextV);
            scoreLog.addView(pointsTextV);
            scoreLog.setOrientation(LinearLayout.HORIZONTAL);
            leaderBoardLinLay.addView(scoreLog);
        }

        return rootView;
    }
}
