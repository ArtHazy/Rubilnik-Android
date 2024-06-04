package com.example.rubilnik.activities.play;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class QuizActivity extends AppCompatActivity {
    static public int questionInd;
    public static Socket socket;
    private JSONObject roommates;
    static public String roomId;
    private String userName;
    static private String userId;

    static public String getUserId(){return userId;}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        String themeName = MainActivity.getThemeString();
//        if (themeName.equals("Theme_Light")) {
//            setTheme(R.style.Theme_Light);
//        } else if (themeName.equals("Theme_Dark")) {
//            setTheme(R.style.Theme_Dark);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Блокировка ориентации на портретный режим
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            socket = IO.socket("http://192.168.0.174:3000").connect();
        } catch (URISyntaxException e) {throw new RuntimeException(e);}

        roomId = (String) getIntent().getExtras().get("roomId");
        userName = (String) getIntent().getExtras().get("userName");
        userId = (String) getIntent().getExtras().get("userId");

        socket.on("connect", args -> {
            if (socket.connected()){
                JSONObject joinReqArgs;
                try {
                    joinReqArgs = new JSONObject();
                    joinReqArgs.append("roomId",roomId);
                    joinReqArgs.append("userName",userName);
                    joinReqArgs.append("userId",userId);
                    Log.d("json",joinReqArgs.toString());
                    socket.emit("join", joinReqArgs);
                } catch (JSONException e) {throw new RuntimeException(e);}
            } else {
                MyTools.alert(this, "Failed to connect the socket");
                this.finish();
            }
        });
        socket.on("join", args -> {
            JSONObject data = (JSONObject) args[0];
            final String userName;
            final String userId;
            try {
                userName = data.getString("userName");
                userId = data.getString("userId");
                roommates = data.getJSONObject("roommates");
                Log.d("erer", roommates.toString());
                runOnUiThread(()->{MyTools.alert(this,userId+" "+userName+" "+getString(R.string.joined));});
            } catch (JSONException e) {MyTools.LogError(e);}
        });
        socket.on("joined", args -> {
            JSONObject data = (JSONObject) args[0];
            if (data.has("roommates")){
                try {
                    roommates = data.getJSONObject("roommates");
                    if (userId==null) {userId = data.getString("guestId");}
                    runOnUiThread(()->MyTools.alert(this, "joined room "+roomId));
                    replaceFragment(new LobbyFragment(roomId));
                } catch (JSONException e) {throw new RuntimeException(e);}
            } else {
                this.finish();
            }
        });

        socket.on("start", args -> {
            runOnUiThread(() -> {MyTools.alert(this,getString(R.string.quizStarted));});
        });

        socket.on("reveal", args -> {
            JSONObject data = (JSONObject) args[0];
            JSONArray correctChoicesInd;
            try {
                correctChoicesInd = data.getJSONArray("correctChoicesInd");
                runOnUiThread(()->MyTools.alert(this,correctChoicesInd.toString()));
            } catch (JSONException e) {throw new RuntimeException(e);}
        });

        socket.on("next", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                JSONObject question = data.getJSONObject("question");
                questionInd = data.getInt("questionInd");
                String text = question.getString("title");
                JSONArray choices = question.getJSONArray("choices");
                replaceFragment(new QuestionFragment(text,choices, questionInd));
//                replaceFragment(new QuestionFragment(text,choices,questionInd));
            } catch (JSONException e) {throw new RuntimeException(e);}
        });

        socket.on("leave", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String userId = data.getString("userId");
                String userName = data.getString("userName");
                runOnUiThread(()->{MyTools.alert(this,userId+" "+userName+" "+getString(R.string.leftTheRoom));});
            } catch (JSONException e) {MyTools.LogError(e);}
        });

        socket.on("scores", args -> {
            JSONObject data = (JSONObject) args[0];
            JSONArray scores;
            try {
                scores = data.getJSONArray("usersScores");
                Log.d("erer", scores.toString());
                replaceFragment(new ResultFragment(scores));
            }
            catch (JSONException e) {MyTools.LogError(e);}
        });

        socket.on("bark", args -> {
            JSONObject data = (JSONObject) args[0];
            String msg = "";
            try {
                msg = data.getString("msg");
            } catch (JSONException e) {MyTools.LogError(e);}
            String finalMsg = msg;
            runOnUiThread(()->{MyTools.alert(this,getString(R.string.bark));});
        });
    }

    public void replaceFragment(Fragment fragment) {
        if (!isDestroyed()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.quizFrame, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);

        builder.setView(dialogView);

        Button btnDlgCancel = dialogView.findViewById(R.id.btn_dlg_cancel);
        Button btnDlgOk = dialogView.findViewById(R.id.btn_dlg_ok);

        AlertDialog dialog = builder.create();
        btnDlgCancel.setOnClickListener((v) -> {
            // Действия при нажатии на кнопку
            dialog.dismiss(); // Закрыть диалог
        });
        btnDlgOk.setOnClickListener((v) -> {
            // Действия при нажатии на кнопку
            finish(); // Закрыть активити
        });
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off();
    }
}
