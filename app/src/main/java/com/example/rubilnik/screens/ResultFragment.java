package com.example.rubilnik.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ResultFragment extends Fragment {


    JSONArray scores;
    public ResultFragment(JSONArray scores) {
        this.scores = scores;
    }
    Button btnReturn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);
        LinearLayout leaderBoardLinLay = rootView.findViewById(R.id.leaderBoardLinLay);
        btnReturn = rootView.findViewById(R.id.btnReturn);

        for (int i=0; i<scores.length();i++){
            String userId="";
            String userName="";
            String scoreS ="";
            try {
                JSONObject score = scores.getJSONObject(i);

                userId = score.getString("userId");
                userName = score.getString("userName");
                Toast.makeText(rootView.getContext(),userId,Toast.LENGTH_SHORT).show();
                Toast.makeText(rootView.getContext(),userName,Toast.LENGTH_SHORT).show();
                int scoreInt = score.getInt("score");
                scoreS = String.valueOf(scoreInt);

            } catch (JSONException e) {MyTools.LogError(e);}

            TextView userIdTextView = new TextView(rootView.getContext());
            userIdTextView.setText(userId);
            TextView userNameTextView = new TextView(rootView.getContext());
            userNameTextView.setText(userName);
            TextView scoreIntTextView = new TextView(rootView.getContext());
            scoreIntTextView.setText(scoreS);

            LinearLayout linearLayout = new LinearLayout(rootView.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            int padding = MyTools.dpToPx(rootView.getContext(),8);
            linearLayout.setPadding(padding,padding,padding,padding);
            linearLayout.addView(userIdTextView);
            linearLayout.addView(userNameTextView);
            linearLayout.addView(scoreIntTextView);

            leaderBoardLinLay.addView(linearLayout);

        }

        btnReturn.setOnClickListener(v -> {
//            requireActivity().finishActivity(0);
        });

        return rootView;
    }
}
