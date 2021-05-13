package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;

public class sing_up_sick extends AppCompatActivity {
    HashMap<String,String> sickmap = new HashMap<String,String>();
    Button CreatAccount ;
    EditText[] editTexts = new EditText[8];
    RadioButton[] radioButton = new RadioButton[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_sick);
        editTexts[0] = (EditText) findViewById(R.id.et_first_name);
        editTexts[1] = (EditText) findViewById(R.id.et_last_name);
        editTexts[2] = (EditText) findViewById(R.id.et_email);
        editTexts[3] = (EditText) findViewById(R.id.ed_password);
        editTexts[4] = (EditText) findViewById(R.id.et_check_email);
        editTexts[5] = (EditText) findViewById(R.id.et_mobile_phone);
        editTexts[6] = (EditText)findViewById(R.id.et_mobile_phone2);
        editTexts[7] = (EditText)findViewById(R.id.ed_birthday);
        radioButton[0] = (RadioButton)findViewById(R.id.rb_female);
        radioButton[1] = (RadioButton)findViewById(R.id.rb_male);
        CreatAccount = (Button)findViewById(R.id.btn_create_account);
        CreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sickmap.put("S_First_Name",editTexts[0].getText().toString());
                sickmap.put("S_Last_Name",editTexts[1].getText().toString());
                sickmap.put("Email",editTexts[2].getText().toString());
                sickmap.put("Password",editTexts[3].getText().toString());
                sickmap.put("Check_Email",editTexts[4].getText().toString());
                sickmap.put("Phone_Mobile",editTexts[5].getText().toString());
                sickmap.put("Phone_Earth",editTexts[6].getText().toString());
                sickmap.put("S_Birthday",editTexts[7].getText().toString());
                if (radioButton[0].isChecked()){
                    sickmap.put("S_Gender",radioButton[0].getText().toString());
                }else {
                    sickmap.put("S_Gender",radioButton[1].getText().toString());
                }
            }
        });
    }
}