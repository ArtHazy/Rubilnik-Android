package com.example.rubilnik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.rubilnik.screens.MainFragment;
import com.example.rubilnik.screens.ScannerQRFragment;
import com.example.rubilnik.screens.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;


public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static Socket mSocket;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    public static Context context;

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
        replaceFragment(new ScannerQRFragment());


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.main)
                replaceFragment(new MainFragment());
            else if (id == R.id.scanner)
                replaceFragment(new ScannerQRFragment());
            else if (id == R.id.settings)
                replaceFragment(new SettingsFragment());
            return true;
        });

        IO.Options options = new IO.Options();
        options.timeout = 5000;
        try {
            mSocket = IO.socket("http://10.0.2.2:3000",options); // TODO 10.0.2.2
        } catch (URISyntaxException e) {MyTools.LogError(e);}


        mSocket.on(Socket.EVENT_CONNECT, args -> {
            runOnUiThread(() -> {MyTools.alert(context, getString(R.string.socketConnected));});
            MainFragment.btnConnect.setText(R.string.connect);
            MainFragment.btnConnect.setClickable(true);
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            runOnUiThread(()-> {MyTools.alert(context,getString(R.string.failedToConnectSocket));});
            MainFragment.btnConnect.setText(R.string.connect);
            MainFragment.btnConnect.setClickable(true);
        });
        mSocket.on("joined", args -> {
            JSONObject data = (JSONObject) args[0];
            JSONArray roommates = null;
            try {
                roommates = data.getJSONArray("roommates");
            } catch (JSONException e) {MyTools.LogError(e);}
            if (roommates.length()>0) { // подключен успешно (переход на quiz activity)
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                // Optional parameters
                quizIntent.putExtra("roommates", roommates.toString());
                //quizIntent.putExtra("currentRoomId", MainFragment.editTextKey.getText().toString());
                //
                startActivity(quizIntent);
            } else {
                mSocket.disconnect();
                runOnUiThread(()->{MyTools.alert(context,getString(R.string.roomDoesntExist));});
            }
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

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() == R.id.main) {
            super.onBackPressed();
        }
        replaceFragment(new MainFragment());
        bottomNavigationView.setSelectedItemId(R.id.main);
    }

}