package com.example.rubilnik.activities.main;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rubilnik.MyTools;
import com.example.rubilnik.activities.play.QuizActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequest extends Thread {
    private static int status = -1;

    private final String roomID;

    public HTTPRequest(String roomID) {
        this.roomID = roomID;
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL("http://192.168.0.174:3000/checkRoomAvailability");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);


            JSONObject postData = new JSONObject();
            postData.put("roomID", roomID);

            OutputStream os = con.getOutputStream();
            os.write(postData.toString().getBytes());
            os.flush();

            status = con.getResponseCode();

            Log.d("HTTP", String.valueOf(status));

            con.disconnect();

//            if (status == 200 && isNOTEmpty(MainActivity.userName)) {
//                Intent playIntent = new Intent(MainActivity.get, QuizActivity.class);
//                playIntent.putExtra("roomId", roomID);
//                playIntent.putExtra("userName", MainActivity.userName);
//                startActivity(playIntent);
//            } else {
//                Toast.makeText(requireContext(), "Неверный код сессии!", Toast.LENGTH_SHORT).show();
//            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

//    public boolean isNOTEmpty(String text) {
//        if (text.isEmpty()) {
//            // Создание уведомления о незаполненном поле
//            Toast.makeText(requireContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else return true;
//    }

    public static int getStatus() {
        return status;
    }
}
