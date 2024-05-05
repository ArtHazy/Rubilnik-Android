package com.example.rubilnik.screens;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rubilnik.MainActivity;
import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainFragment extends Fragment {
    public static androidx.appcompat.widget.AppCompatButton btnConnect;
//    androidx.appcompat.widget.AppCompatButton usernameButton;

    public View rootView;

    public EditText editTextKey;
    public EditText editTextUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextUsername = rootView.findViewById(R.id.editTextName);
        //usernameButton = rootView.findViewById(R.id.usernameButton);
        editTextKey = rootView.findViewById(R.id.editTextKey);

        //SCANNER RESULT
        editTextKey.setText(ScannerQRFragment.code);

        btnConnect.setOnClickListener((View v) ->{
            btnConnect.setClickable(false);
            btnConnect.setText("...");
            MainActivity.mSocket.connect();

            JSONObject data = new JSONObject();

            try {
                String roomId = editTextKey.getText().toString();
                data.put("roomId",roomId);
                data.put("userName",editTextUsername.getText().toString());
                MainActivity.mSocket.emit("join",data);
            } catch (JSONException e) {MyTools.LogError(e);}
        });
        return rootView;
    }
}