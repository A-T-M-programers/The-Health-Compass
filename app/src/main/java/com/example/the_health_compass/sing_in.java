package com.example.the_health_compass;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sing_in extends AppCompatActivity {
    private TextView create_account;
    private Button sing_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        create_account = (TextView) findViewById(R.id.tv_create_account);
        sing_in_btn = (Button) findViewById(R.id.btn_sign_in);
        loading_screen loadingScreen = new loading_screen(sing_in.this);


        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingScreen.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingScreen.dismissDialog();
                    }
                }, 5000);
            }
        });
        loadingScreen.startLoadingDialog();
    }

    public void openCreateAccountPage() {
        Intent intent = new Intent(this, sign_up.class);
        startActivity(intent);
    }
}