package com.example.rubilnik.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.QuizActivity;
import com.example.rubilnik.R;

public class WaitingFragment extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitchThemes;
    TextView textViewDownload;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.waiting_fragment, container, false);
        aSwitchThemes = rootView.findViewById(R.id.swWaitingThemes);
        textViewDownload = rootView.findViewById(R.id.txtDownload);
        textViewDownload.setText("Комната "+QuizActivity.currentRoomId);

        aSwitchThemes.setOnCheckedChangeListener ((buttonView, isChecked) -> {
            if (!isChecked) {
                rootView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyDark));
                textViewDownload.setTextColor(ContextCompat.getColor(requireContext(), R.color.cineLight));
            } else {
                rootView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyLight));
                textViewDownload.setTextColor(ContextCompat.getColor(requireContext(), R.color.cineDark));
            }
        });

        return rootView;
    }
}
