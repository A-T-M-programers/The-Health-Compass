package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class sing_in extends AppCompatActivity {

    private TextView create_account ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_sick);
        create_account =(TextView)findViewById(R.id.tv_create_account);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountPage();
            }
        });
    }
    public void openCreateAccountPage()
    {
        Intent intent = new Intent(this,sign_up.class);
        startActivity(intent);
    }
}