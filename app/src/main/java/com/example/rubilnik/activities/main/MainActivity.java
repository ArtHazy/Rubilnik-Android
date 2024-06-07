package com.example.rubilnik.activities.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rubilnik.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
//    NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        String themeName = pref.getString(SettingsFragment.getPrefTheme(), "Theme_Dark");
        if (themeName.equals("Theme_Light")) {
            setTheme(R.style.Theme_Light);
        } else if (themeName.equals("Theme_Dark")) {
            setTheme(R.style.Theme_Dark);
        }

//        Toast.makeText(this, themeName, Toast.LENGTH_SHORT).show();

        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Блокировка ориентации на портретный режим
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setHapticFeedbackEnabled(true);

//        FragmentManager fragmentManager = getSupportFragmentManager();
        //NAVIGATION
        // Register event handlers
        //NAVIGATION
        bottomNavigationView = findViewById(R.id.menuBottom);
        replaceFragment(new ScannerQRFragment());

//        JoinFragment joinFragment = new JoinFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            vibrate();
            if (id == R.id.main) {
                replaceFragment(new JoinFragment());
            }
            else if (id == R.id.scanner)
                replaceFragment(new ScannerQRFragment());
            else if (id == R.id.settings) {
                replaceFragment(new SettingsFragment());
            }
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

    //Ловим перезапуск Активити, когда меняем тему
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        replaceFragment(new SettingsFragment());
    }

    public void vibrate() {
//        long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};
//        int[] mAmplitudes = new int[]{0, 255, 0, 255, 0, 255, 0, 255};
//        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1));
//        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(50, -1));
    }
}