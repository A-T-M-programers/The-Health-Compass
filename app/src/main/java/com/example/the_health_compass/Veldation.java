package com.example.the_health_compass;

import android.widget.TextView;

public class Veldation {
    public static boolean veldation_text(TextView textView){
        boolean result = true;
        String val = textView.getText().toString();
        if (val == null || val.isEmpty()){
            result = false;
        }
        return result;
    }
}
