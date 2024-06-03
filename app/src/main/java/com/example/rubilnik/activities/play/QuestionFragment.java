package com.example.rubilnik.activities.play;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.Objects;

public class QuestionFragment extends Fragment {

    String text;
    JSONArray choices;
    int questionInd;
    public QuestionFragment(String text, JSONArray choices,int questionInd){
        this.questionInd = questionInd;
        this.text=text;
        this.choices=choices;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.question_fragment, container, false);
        Button choiceButton;
        ArrayList<Button> choiceButtons = new ArrayList<>();
        LinearLayout buttonsLayout = rootView.findViewById(R.id.buttonsLayout);
        buttonsLayout.removeAllViews();
        TextView txtQuestion = rootView.findViewById(R.id.txtQuestion);
        TextView txtQuestionInd = rootView.findViewById(R.id.txtQuestionInd);
        txtQuestionInd.setText(String.valueOf(++questionInd));

        ViewGroup decorView = (ViewGroup) requireActivity().getWindow().getDecorView();
        View overlayView = new View(requireActivity());
        ViewGroup.LayoutParams params_blackout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        overlayView.setLayoutParams(params_blackout);
        // Создаем новый полупрозрачный View

        overlayView.setBackgroundColor(Color.parseColor("#8FFFFFFF")); // Устанавливаем цвет и прозрачность слоя
//        decorView.addView(overlayView);

        overlayView.setBackgroundColor(Color.parseColor("#80000000")); // Устанавливаем цвет и прозрачность слоя

        txtQuestion.setText(text);

        String[] chName = getResources().getStringArray(R.array.choiceName);
        int[] chStyle = {R.drawable.btn_choice_state1, R.drawable.btn_choice_state2, R.drawable.btn_choice_state3, R.drawable.btn_choice_state4};
        Typeface typeface = getResources().getFont(R.font.font);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(convertDP(5), convertDP(0), convertDP(5), convertDP(0));
        params.width = convertDP(70);
        params.height = convertDP(70);

        for (int i=0; i<choices.length(); i++){

            JSONObject choice;
            String text ="";
            try {
                choice = choices.getJSONObject(i);
                text = choice.getString("text");
            } catch (JSONException e) {MyTools.LogError(e);}

            if (text.length()>0) { // if choice exists
                choiceButton = new Button(rootView.getContext());
                choiceButton.setTextAppearance(R.style.btnChoice);
                choiceButton.setTypeface(typeface);
                choiceButton.setLayoutParams(params);
                choiceButton.setText(chName[i]);
                choiceButton.setBackgroundResource(chStyle[i]);

                int finalI = i;
                choiceButton.setOnClickListener(v -> {
                    JSONObject data = new JSONObject();
                    int choiceInd = finalI;
                    try {
                        data.put("roomId",QuizActivity.currentRoomId);
//                        data.put("userId",QuizActivity.playerId);
                        data.put("questionInd", QuizActivity.questionInd);
                        data.put("choiceInd", choiceInd);
                    } catch (JSONException e) {throw new RuntimeException(e);}
                    for (Button chButton:choiceButtons) {
                        chButton.setClickable(false);
                    }
                    QuizActivity.socket.emit("choice", data);

                    // Добавляем View поверх текущего содержимого
//                    decorView.addView(overlayView);


                });
                buttonsLayout.addView(choiceButton);
                choiceButtons.add(choiceButton);

//                // Для удаления слоя затемнения
//                decorView.removeView(overlayView);
            }
        }
//        // Для удаления слоя затемнения
//        decorView.removeView(overlayView);
        return rootView;
    }

    int convertDP(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
