package com.example.rubilnik;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rubilnik.screens.MainFragment;
import com.example.rubilnik.screens.QuestionFragment;
import com.example.rubilnik.screens.ResultFragment;
import com.example.rubilnik.screens.WaitingFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class QuizActivity extends AppCompatActivity {
    static public String playerId;
    static public String msg;
    static public String currentRoomId;
    static public int currentQuestionInd;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        runOnUiThread(()->{MyTools.alert(this,"room joined");});

        Intent intent = getIntent();
        playerId = intent.getStringExtra("playerId");
        currentRoomId = intent.getStringExtra("currentRoomId");

        currentQuestionInd=-1;
        replaceFragment(new WaitingFragment());

        MainActivity.mSocket.on("join", onJoin);
        MainActivity.mSocket.on("leave", onLeave);
        MainActivity.mSocket.on("start", onStart);
        MainActivity.mSocket.on("next", onNext);
        MainActivity.mSocket.on("choice", onChoice);
        MainActivity.mSocket.on("reveal", onReveal);
        MainActivity.mSocket.on("end", onEnd);
        MainActivity.mSocket.on("bark", onBark);
    }

    public void replaceFragment(Fragment fragment) {
        if (!isDestroyed()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.quizFrame, fragment);
            fragmentTransaction.commit();
        }

    }
    private Emitter.Listener onJoin = args -> {
        JSONObject data = (JSONObject) args[0];
        String userName = "";
        String userId = "";
        try {
            userName = data.getString("userName");
            userId = data.getString("userId");
        } catch (JSONException e) {MyTools.LogError(e);}
        if (userName.length()>0 && userId.length()>0){
            String finalUserId = userId;
            String finalUserName = userName;
            runOnUiThread(()->{MyTools.alert(this,finalUserId+" "+finalUserName+" joined");});
        }
    };
    private Emitter.Listener onLeave = args -> {
        JSONObject data = (JSONObject) args[0];
        try {
            String userId = data.getString("userId");
            String userName = data.getString("userName");
            runOnUiThread(()->{MyTools.alert(this,userId+" "+userName+" left the room");});
            if (userId==currentRoomId){ // host left
                replaceFragment(new WaitingFragment());
            }
        } catch (JSONException e) {MyTools.LogError(e);}

    };
    private Emitter.Listener onStart = args -> {
        runOnUiThread(() -> {
            MyTools.alert(this,"quiz started");
        });
    };
    private Emitter.Listener onNext = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject data = (JSONObject) args[0];
            try {
                JSONObject question = data.getJSONObject("question");
                currentQuestionInd = data.getInt("questionInd");
                String text = question.getString("text");
                JSONArray choices = question.getJSONArray("choices");
                replaceFragment(new QuestionFragment(text,choices,currentQuestionInd));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };
    private Emitter.Listener onChoice = args -> {
        JSONObject data = (JSONObject) args[0];
        try {
            String userId = data.getString("userId");
            int questionInd = data.getInt("questionInd");
            int choiceInd = data.getInt("choiceInd");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };
    private Emitter.Listener onReveal = args -> {
        JSONObject data = (JSONObject) args[0];
        int choiceInd;
        try {
            choiceInd = data.getInt("choiceInd");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };
    private Emitter.Listener onEnd = args -> {
        JSONObject data = (JSONObject) args[0];
        JSONArray scores = new JSONArray();
        try {scores = data.getJSONArray("scores");} catch (JSONException e) {MyTools.LogError(e);}
        replaceFragment(new ResultFragment(scores));
    };
    private Emitter.Listener onBark = args -> {
        JSONObject data = (JSONObject) args[0];
        String msg = "";
        try {
            msg = data.getString("msg");
        } catch (JSONException e) {MyTools.LogError(e);}
        String finalMsg = msg;
        runOnUiThread(()->{MyTools.alert(this,finalMsg);});
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.mSocket.disconnect();
//        MainActivity.mSocket.off();

        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //MainActivity.mSocket.disconnect();
        //MainActivity.mSocket.off();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.mSocket.disconnect();
        //MainActivity.mSocket.off();
    }
}
