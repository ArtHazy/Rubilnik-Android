package com.example.rubilnik.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rubilnik.R;

public class MainFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnConnect;
    EditText editTextKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextKey = rootView.findViewById(R.id.editTextKey);

        btnConnect.setOnClickListener((View v) ->
                {

                }
        );
        return rootView;
    }
}