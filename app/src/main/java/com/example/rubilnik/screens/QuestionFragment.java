package com.example.rubilnik.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;

public class QuestionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.question_fragment, container, false);
        LinearLayout buttonsLayout = rootView.findViewById(R.id.buttonsLayout);
        Button choiceButton;

        return rootView;
    }
}
