package com.example.rubilnik.activities.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;

public class SettingsFragment extends Fragment {

    Switch swTheme;
    Button btnFeedback;
    private static final String PREF_THEME = "theme";

    private static final String APP_PREFERENCES = JoinFragment.APP_PREFERENCES;
    private static SharedPreferences preferences;
    public static String value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        swTheme = rootView.findViewById(R.id.swTheme);
        btnFeedback = rootView.findViewById(R.id.btnFeedback);

        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        SharedPreferences pref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String themeName = pref.getString(SettingsFragment.getPrefTheme(), "Theme_Dark");
        if (themeName.equals("Theme_Light")) {
            swTheme.setChecked(false);
        } else if (themeName.equals("Theme_Dark")) {
            swTheme.setChecked(true);
        }

        swTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                value = "Theme_Dark";
                safeTheme();
            } else {
                value = "Theme_Light";
                safeTheme();
            }
            requireActivity().recreate(); // Пересоздаем активность для применения изменений
        });

        btnFeedback.setOnClickListener((v) -> {
            //!!!!!!!!!!!!
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PREF_THEME, value);
    }

    public static void safeTheme() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_THEME, value);
        editor.apply();
    }

    public static String getPrefTheme() { return PREF_THEME; }
}