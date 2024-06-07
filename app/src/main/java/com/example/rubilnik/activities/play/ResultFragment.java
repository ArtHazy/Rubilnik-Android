package com.example.rubilnik.activities.play;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;
import com.example.rubilnik.activities.list.ListAdapter;
import com.example.rubilnik.activities.list.ListData;
import com.example.rubilnik.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {

    public Button btnReturn;
    JSONArray scores;
    public ResultFragment(JSONArray scores) {
        this.scores = scores;
    }

    ListView listView;
    ListAdapter listAdapter;
    ListData listData;
    ArrayList<ListData> dataArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment, container, false);
//        LinearLayout leaderBoardLinLay = rootView.findViewById(R.id.leaderBoardLinLay);
        btnReturn = rootView.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> {
            requireActivity().finish();
        });

        //list
//        int[] imageList = {R.drawable.user_profile};
        List<String> placeList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        List<String> scoreList = new ArrayList<String>();

        for (int i=0; i<scores.length();i++){
            String id="";
            // if ( id.equals(QuizActivity.getUserId())) {} //!
            String name="";
            Integer points=null;
            try {
                JSONObject score = scores.getJSONObject(i);
                id = score.getString("userId");
                points = score.getInt("userScore");
                name = score.getString("userName");
            } catch (JSONException e) {MyTools.LogError(e);}
            TextView placeTextV = new TextView(this.getContext());
            placeTextV.setText(String.valueOf(i+1));
            TextView idTextV = new TextView(this.getContext());
            idTextV.setText(id);
//            TextView nameTextV = new TextView(this.getContext());

//            nameTextV.setText(name);
//            TextView pointsTextV = new TextView(this.getContext());
//            if (points!=null) {pointsTextV.setText(points.toString());}

//            LinearLayout scoreLog = new LinearLayout(this.getContext());
//            View v = new View(this.getContext());

            placeList.add(String.valueOf(i));
            nameList.add(name);
            if (points!=null)
                scoreList.add(points.toString());

//            scoreLog.addView(placeTextV);
//            scoreLog.addView(idTextV);
//            scoreLog.addView(nameTextV);
//            scoreLog.addView(pointsTextV);

//            scoreLog.setOrientation(LinearLayout.HORIZONTAL);
//            leaderBoardLinLay.addView(scoreLog);
        }

        for (int i = 0; i < nameList.size(); i++){
            listData = new ListData(placeList.get(i), nameList.get(i), scoreList.get(i));
            dataArrayList.add(listData);
        }

        listView = rootView.findViewById(R.id.listView);
        listAdapter = new ListAdapter(requireActivity(), dataArrayList);
        listView.setAdapter(listAdapter);

        return rootView;
    }
}
