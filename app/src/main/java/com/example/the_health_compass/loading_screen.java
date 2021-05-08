package com.example.the_health_compass;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loading_screen {
    private Activity activity;
    private AlertDialog alertDialog;
    loading_screen(Activity MyActivity){
        activity=MyActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.loading_screen,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    void dismissDialog(){
        alertDialog.dismiss();
    }
}
