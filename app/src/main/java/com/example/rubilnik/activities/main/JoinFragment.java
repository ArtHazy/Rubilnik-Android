package com.example.rubilnik.activities.main;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rubilnik.R;
import com.example.rubilnik.activities.play.QuizActivity;

public class JoinFragment extends Fragment {
    public static androidx.appcompat.widget.AppCompatButton btnConnect;
//    androidx.appcompat.widget.AppCompatButton usernameButton;

    public View rootView;
    public EditText editTextRoomId;
    public EditText editTextUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextUserName = rootView.findViewById(R.id.editTextName);
        //usernameButton = rootView.findViewById(R.id.usernameButton);
        editTextRoomId = rootView.findViewById(R.id.editTextKey);

        //SCANNER RESULT
        editTextRoomId.setText(ScannerQRFragment.code);

        btnConnect.setOnClickListener((v) -> {
            Intent playIntent = new Intent(this.getContext(), QuizActivity.class);
            playIntent.putExtra("roomId",editTextRoomId.getText().toString().trim());
            playIntent.putExtra("userName",editTextUserName.getText().toString().trim());
            //playIntent.putExtra("userId",userId);
            startActivity(playIntent);
        });
        return rootView;
    }
}