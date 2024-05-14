package com.example.rubilnik.activities.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;
import com.example.rubilnik.activities.play.QuizActivity;

import java.io.IOException;

public class JoinFragment extends Fragment {
    public static androidx.appcompat.widget.AppCompatButton btnConnect;
//    androidx.appcompat.widget.AppCompatButton usernameButton;

    private EditText editTextRoomId;
    private static EditText editTextUserName;

    private static final String PREF_NAME = "userName";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.join_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextUserName = rootView.findViewById(R.id.editTextName);
        editTextRoomId = rootView.findViewById(R.id.editTextKey);

        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
            editTextUserName.setText(savedInstanceState.getString(PREF_NAME));
        }

        //SCANNER RESULT
        editTextRoomId.setText(ScannerQRFragment.code);

        //check input in textEdits
        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                upperCase(s, editTextUserName);

                //Сохранение данных
                editor = preferences.edit();
                editor.putString(PREF_NAME, s.toString());
                editor.apply();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        editTextRoomId.addTextChangedListener(new TextWatcher() {
            int cursorPosition;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorPosition = editTextRoomId.getSelectionStart(); // Сохраняем позицию курсора
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                upperCase(s, editTextRoomId);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnConnect.setOnClickListener((v) -> {
            try {
                connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Восстановление данных при создании представления фрагмента
        editTextUserName.setText(preferences.getString(PREF_NAME, ""));

        return rootView;
    }

    private void upperCase(CharSequence s, EditText editText) {
        // Необходимо проверить, что пользователь вводит текст, чтобы избежать зацикливания
        if (!s.toString().equals(s.toString().toUpperCase())) {
            editText.setText(s.toString().toUpperCase());
            editText.setSelection(editText.getText().length()); // Перемещает курсор в конец текста
        }
    }

    private void connect() throws IOException {
        String roomID = editTextRoomId.getText().toString().trim();

//        HTTPRequest httpRequest = new HTTPRequest(roomID);
//        httpRequest.run();
//        int errorCode = httpRequest.getStatus(); errorCode == 200 &&

        if (isNOTEmpty(editTextUserName.getText().toString())) {
            Intent playIntent = new Intent(this.getContext(), QuizActivity.class);
            playIntent.putExtra("roomId", roomID);
            playIntent.putExtra("userName", editTextUserName.getText().toString());
            startActivity(playIntent);
        } else {
            Toast.makeText(requireContext(), "Неверный код сессии!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNOTEmpty(String text) {
        if (text.isEmpty()) {
            // Создание уведомления о незаполненном поле
            Toast.makeText(requireContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PREF_NAME, editTextUserName.getText().toString());
    }

    public static String getUserName() {
        return editTextUserName.getText().toString();
    }
}