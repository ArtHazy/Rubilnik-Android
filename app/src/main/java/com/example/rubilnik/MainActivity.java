package com.example.rubilnik;

import static androidx.navigation.ui.ActivityKt.setupActionBarWithNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.rubilnik.screens.MainFragment;
import com.example.rubilnik.screens.SettingsFragment;
import com.example.rubilnik.screens.WaitingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static Socket mSocket;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    public static Context context;

    Button btnConnect;

//    private ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        fragmentManager = getSupportFragmentManager();
        //NAVIGATION
        // Register event handlers
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

        try {
            mSocket = IO.socket("http://10.0.2.2:3000"); // TODO 10.0.2.2
        } catch (URISyntaxException e) {MyTools.LogError(e);}

        mSocket.on("joined", onJoined);
    }
    private Emitter.Listener onJoined = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String playerId = "";
            try {
                playerId = data.getString("playerId");
            } catch (JSONException e) {MyTools.LogError(e);}
            if (playerId.length()>0) { // подключен успешно (переход на quiz activity)
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                // Optional parameters
                quizIntent.putExtra("playerId", playerId);
                quizIntent.putExtra("currentRoomId", MainFragment.editTextKey.getText().toString());
                //
                startActivity(quizIntent);
            } else {
                runOnUiThread(()->{
                    MyTools.alert(context,"room doesn't exist");
                    mSocket.disconnect();
                });
            }
        }
    };

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

}