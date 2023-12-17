package com.example.rubilnik;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MyTools {
    public static void LogError(Exception e){
        Log.d("my", e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static LinearLayout customListFormat(ArrayList<View> elements, Context context, boolean useCustomBackground, int marginBetweenElements, boolean useDefaultElementsPadding) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams elementLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        elementLayoutParams.setMargins(0, 0, 0, marginBetweenElements);

        for (int i = 0; i < elements.size(); i++) {
            View element = elements.get(i);
            if (i != elements.size() - 1) {
                element.setLayoutParams(elementLayoutParams);
            }
            if (useCustomBackground) {
                if (elements.size()<2){
                    element.setBackground(context.getDrawable(R.drawable.component));
                } else if (i==0) {
                    element.setBackground(context.getDrawable(R.drawable.component_1st));
                } else if (i == elements.size() - 1) {
                    element.setBackground(context.getDrawable(R.drawable.component_last));
                } else {
                    element.setBackground(context.getDrawable(R.drawable.component_middl));
                }
            }
            if (useDefaultElementsPadding){
                element.setPadding(dpToPx(context, 16), dpToPx(context, 16), dpToPx(context, 16), dpToPx(context, 16));
            }

            linearLayout.addView(element);
        }

        return linearLayout;
    }

}
