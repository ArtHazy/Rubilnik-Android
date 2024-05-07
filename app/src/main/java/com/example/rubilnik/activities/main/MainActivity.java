package com.example.rubilnik.activities.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.R;
import com.example.rubilnik.activities.play.LobbyFragment;
import com.example.rubilnik.activities.play.QuizActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class MainActivity extends AppCompatActivity {
//    NavController navController;
    private BottomNavigationView bottomNavigationView;

    public static String userName = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Блокировка ориентации на портретный режим
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //NAVIGATION
        // Register event handlers
        //NAVIGATION
        bottomNavigationView = findViewById(R.id.menuBottom);
        replaceFragment(new ScannerQRFragment());

//        JoinFragment joinFragment = new JoinFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.main) {
                replaceFragment(new JoinFragment());
                Toast.makeText(this, MainActivity.userName, Toast.LENGTH_SHORT).show();
            }
            else if (id == R.id.scanner)
                replaceFragment(new ScannerQRFragment());
            else if (id == R.id.settings)
                replaceFragment(new SettingsFragment());
            return true;
        });

//        IO.Options options = new IO.Options();
//        options.timeout = 5000;


        //        Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
//                // Optional parameters
//                quizIntent.putExtra("roommates", roommates.toString());
//                //quizIntent.putExtra("currentRoomId", MainFragment.editTextKey.getText().toString());
//                //
//                startActivity(quizIntent);


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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.main) {
//            android.app.Fragment fragment = getFragmentManager().findFragmentById(R.layout.lobby_fragment);
//            if(fragment.isVisible()) {
//                replaceFragment(new JoinFragment());
//            }
////            notification for EXIT
//            else
                super.onBackPressed();
        }
        replaceFragment(new JoinFragment());
        bottomNavigationView.setSelectedItemId(R.id.main);
    }
}