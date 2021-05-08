package com.example.the_health_compass;

import android.widget.TextView;

public class Veldation {
    public static boolean veldation_text(TextView textView){
        boolean result = true;
        String val = textView.getText();
        if (val==""||val==null){
            result = false;
        }
        return result;
    }
}
