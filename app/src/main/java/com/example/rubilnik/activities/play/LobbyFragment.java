package com.example.rubilnik.activities.play;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;
import com.example.rubilnik.activities.main.JoinFragment;

public class LobbyFragment extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitchThemes;
    TextView textViewDownload, textViewUserName, textViewWaiting;
    String roomId;

    LobbyFragment(String roomId){
        this.roomId = roomId;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lobby_fragment, container, false);
        aSwitchThemes = rootView.findViewById(R.id.swWaitingThemes);
        textViewDownload = rootView.findViewById(R.id.txtDownload);
        textViewUserName = rootView.findViewById(R.id.textViewUserName);
        textViewWaiting = rootView.findViewById(R.id.textViewWaiting);
        textViewDownload.setText(getString(R.string.room)+" "+roomId);
        textViewUserName.setText(JoinFragment.getUserName());

        aSwitchThemes.setOnClickListener ((v) -> {
            rootView.setBackgroundColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.attr.greyMain));
            textViewDownload.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.attr.cine));
            textViewWaiting.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.attr.greyHint));
            textViewUserName.setTextColor(ContextCompat.getColor(LobbyFragment.this.requireContext(), R.attr.yellow));
        });
        return rootView;
    }
}
