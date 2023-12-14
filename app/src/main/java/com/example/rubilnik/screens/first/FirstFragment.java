package com.example.rubilnik.screens.first;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rubilnik.MainActivity;
import com.example.rubilnik.R;

import java.util.Objects;

public class FirstFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnConnect;
    EditText editTextKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//


        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextKey = rootView.findViewById(R.id.editTextKey);

        btnConnect.setOnClickListener((View v) ->
            {

            }
        );

//        editTextKey.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });

        return rootView;
    }
}