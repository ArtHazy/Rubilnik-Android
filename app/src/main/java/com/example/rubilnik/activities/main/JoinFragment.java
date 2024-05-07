package com.example.rubilnik.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rubilnik.R;
import com.example.rubilnik.activities.play.QuizActivity;

import java.util.Objects;

public class JoinFragment extends Fragment {
    public static androidx.appcompat.widget.AppCompatButton btnConnect;
//    androidx.appcompat.widget.AppCompatButton usernameButton;

    private EditText editTextRoomId;
    private EditText editTextUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.join_fragment, container, false);
        btnConnect = rootView.findViewById(R.id.btnConnect);
        editTextUserName = rootView.findViewById(R.id.editTextName);
        editTextRoomId = rootView.findViewById(R.id.editTextKey);

        //SCANNER RESULT
        editTextRoomId.setText(ScannerQRFragment.code);

        if (!Objects.equals(MainActivity.userName, "test"))
            editTextUserName.setText(MainActivity.userName);

        //check input in textEdits
        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                upperCase(s, editTextUserName);
                // Обновите переменную при изменении текста в EditText
                MainActivity.userName = s.toString();
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
            connect();
        });
        return rootView;
    }

    private void upperCase(CharSequence s, EditText editText) {
        // Необходимо проверить, что пользователь вводит текст, чтобы избежать зацикливания
        if (!s.toString().equals(s.toString().toUpperCase())) {
            editText.setText(s.toString().toUpperCase());
            editText.setSelection(editText.getText().length()); // Перемещает курсор в конец текста
        }
    }

    private void connect() {
        if (isNOTEmpty(MainActivity.userName)) {
            Intent playIntent = new Intent(this.getContext(), QuizActivity.class);
            playIntent.putExtra("roomId", editTextRoomId.getText().toString().trim());
            playIntent.putExtra("userName", MainActivity.userName);
            startActivity(playIntent);
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
}