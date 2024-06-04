package com.example.rubilnik.activities.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;

public class LobbyFragment extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitchThemes;
    TextView textViewDownload, textViewUserName, textViewWaiting;
    String roomId;
    public static final String PREF_NAME = "userName";
    public static final String APP_PREFERENCES = "settings";

    LobbyFragment(String roomId){
        this.roomId = roomId;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String userName = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(PREF_NAME, "TEST");

        View rootView = inflater.inflate(R.layout.lobby_fragment, container, false);
        aSwitchThemes = rootView.findViewById(R.id.swWaitingThemes);
        textViewDownload = rootView.findViewById(R.id.txtDownload);
        textViewUserName = rootView.findViewById(R.id.textViewUserName);
        textViewWaiting = rootView.findViewById(R.id.textViewWaiting);
        textViewDownload.setText(getString(R.string.room)+" "+roomId);
        textViewUserName.setText(userName);

        aSwitchThemes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rootView.setBackgroundColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.greyDark));
                textViewDownload.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.cineLight));
                textViewWaiting.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.greyHintLight));
                textViewUserName.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.yellowLight));
            } else {
                rootView.setBackgroundColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.greyLight));
                textViewDownload.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.cineDark));
                textViewWaiting.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.greyHintDark));
                textViewUserName.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.color.yellowDark));
            }
        });
        return rootView;
    }
}
