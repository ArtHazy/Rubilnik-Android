package com.example.rubilnik.screens;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rubilnik.MainActivity;
import com.example.rubilnik.R;
import com.example.rubilnik.SetUserNameDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.socket.client.Socket;

public class MainFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnConnect;
    androidx.appcompat.widget.AppCompatButton usernameButton;

    Socket mSocket;
    EditText editTextKey;
    EditText editTextUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSocket = ((MainActivity) requireActivity()).mSocket;

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextUsername = rootView.findViewById(R.id.editTextName);
        //usernameButton = rootView.findViewById(R.id.usernameButton);
        editTextKey = rootView.findViewById(R.id.editTextKey);

        btnConnect.setOnClickListener((View v) ->
                {
                    mSocket.connect();
                    JSONObject data = new JSONObject();

                    try {
                        data.put("roomId",editTextKey.getText().toString());
                        data.put("userName",editTextUsername.getText().toString());
                        mSocket.emit("join",data);
                        Toast.makeText(rootView.getContext(), "hi", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return rootView;
    }
}