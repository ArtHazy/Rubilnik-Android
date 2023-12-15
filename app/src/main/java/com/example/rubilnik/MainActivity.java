package com.example.rubilnik;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.graphics.drawable.DrawableCompat.inflate;
import static androidx.navigation.ui.ActivityKt.setupActionBarWithNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rubilnik.screens.MainFragment;
import com.example.rubilnik.screens.AccountFragment;
import com.example.rubilnik.screens.QuestionFragment;
import com.example.rubilnik.screens.SettingsFragment;
import com.example.rubilnik.screens.WaitingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.Console;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    public Socket mSocket;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    Context context = MainActivity.this;

    Button btnConnect;

//    private ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NAVIGATION
        try {
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {
            Log.d("my", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        // Register event handlers
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on("joined", onJoined);
        mSocket.on("join", onJoin);
        mSocket.on("start", onStart);
        mSocket.on("next", onNext);
        mSocket.on("choice", onChoice);
        mSocket.on("reveal", onReveal);
        mSocket.on("end", onEnd);
        mSocket.on("bark", onBark);



        //NAVIGATION
        bottomNavigationView = findViewById(R.id.menuBottom);
        replaceFragment(new MainFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.main)
                replaceFragment(new MainFragment());
            else if (id == R.id.waiting)
                replaceFragment(new WaitingFragment());
            else if (id == R.id.settings)
                replaceFragment(new SettingsFragment());
            return true;
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    ///////
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Connected to the server
        }
    };

    private void alert(String s){
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }
    String alertMessage;
    Runnable alert = new Runnable(){
        @Override
        public void run() {
            alert(alertMessage);
        }
    };
    private Emitter.Listener onJoined = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String playerId = "";
            String msg = "";
            try {
                playerId = data.getString("playerId");
                msg = data.getString("msg");
            } catch (JSONException e) {
                alertMessage = "exception";
                runOnUiThread(alert);
                Log.d("my", e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            if (playerId.length()>0) { // подключен успешно (переход на стр ожидания)
                replaceFragment(new WaitingFragment());
                alertMessage = "connected to the room";
                runOnUiThread(alert);
                //Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
                //alert("connected to the room");
            } else { // ошибка подключения (сообщение об ошибке)
                alertMessage = playerId.toString(); //"failed to connect to the room"
                runOnUiThread(alert);
                //alert("failed to connect to the room");
            }
        }
    };

    private Emitter.Listener onJoin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String userName;
            String userId;
            try {
                userName = data.getString("userName");
                userId = data.getString("userId");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private Emitter.Listener onStart = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener onNext = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                String text = data.getString("text");
                JSONArray choices = data.getJSONArray("choices");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private Emitter.Listener onChoice = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                String userId = data.getString("userId");
                int questionInd = data.getInt("questionInd");
                int choiceInd = data.getInt("choiceInd");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private Emitter.Listener onReveal = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            int choiceInd;
            try {
                choiceInd = data.getInt("choiceInd");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private Emitter.Listener onEnd = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            JSONArray scores;
            try {
                scores = data.getJSONArray("scores");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };



    private Emitter.Listener onBark = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String msg;
            try {
                msg = data.getString("msg");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //alert(msg);
            // Handle "myEvent" event
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}