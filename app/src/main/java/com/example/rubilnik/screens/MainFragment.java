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
import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;
import com.example.rubilnik.SetUserNameDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainFragment extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnConnect;
    androidx.appcompat.widget.AppCompatButton usernameButton;

    Socket mSocket;
    public static EditText editTextKey;
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

        btnConnect.setOnClickListener((View v) ->{
            btnConnect.setClickable(false);
            btnConnect.setText("...");
            //mSocket = new Socket(IO.socket(""));
            if (!mSocket.connected()){mSocket.connect();}

            mSocket.on(Socket.EVENT_CONNECT, args -> {
                btnConnect.setText(R.string.connect);
                btnConnect.setClickable(true);

                JSONObject data = new JSONObject();

                try {
                    String roomId = editTextKey.getText().toString();
                    data.put("roomId",roomId);
                    data.put("userName",editTextUsername.getText().toString());
                    mSocket.emit("join",data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                btnConnect.setText(R.string.connect);
                btnConnect.setClickable(true);
                mSocket.disconnect();
            });
        });
        return rootView;
    }
}