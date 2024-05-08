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
import com.example.rubilnik.activities.main.MainActivity;

public class LobbyFragment extends Fragment {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitchThemes;
    TextView textViewDownload, textViewUserName, textViewWaiting;
    String roomId;

    LobbyFragment(String roomId){
        this.roomId = roomId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lobby_fragment, container, false);
        aSwitchThemes = rootView.findViewById(R.id.swWaitingThemes);
        textViewDownload = rootView.findViewById(R.id.txtDownload);
        textViewUserName = rootView.findViewById(R.id.textViewUserName);
        textViewWaiting = rootView.findViewById(R.id.textViewWaiting);
        textViewDownload.setText(getString(R.string.room)+" "+roomId);
        textViewUserName.setText(MainActivity.userName);

        aSwitchThemes.setOnCheckedChangeListener ((buttonView, isChecked) -> {
            if (!isChecked) {
                rootView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyDark));
                textViewDownload.setTextColor(ContextCompat.getColor(requireContext(), R.color.cineLight));
                textViewWaiting.setTextColor(ContextCompat.getColor(requireContext(), R.color.greyHintLight));
                textViewUserName.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow));
            } else {
                rootView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyLight));
                textViewDownload.setTextColor(ContextCompat.getColor(requireContext(), R.color.cineDark));
                textViewWaiting.setTextColor(ContextCompat.getColor(requireContext(), R.color.greyHint));
                textViewUserName.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellowDark));
            }
        });
        return rootView;
    }
}
