package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class sign_up extends AppCompatActivity {
    HashMap<String, String> Doctormap = new HashMap<>();
    Button CreateAccoubt;
    Doctor d = new Doctor();
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButtons = new RadioButton[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTexts[0] = (EditText) findViewById(R.id.et_full_name);
        editTexts[1] = (EditText) findViewById(R.id.et_email);
        editTexts[2] = (EditText) findViewById(R.id.et_check_email);
        editTexts[3] = (EditText) findViewById(R.id.et_password);
        editTexts[4] = (EditText) findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText) findViewById(R.id.et_birthday);
        radioButtons[0] = (RadioButton) findViewById(R.id.rb_female);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb_male);
        radioButtons[2] = (RadioButton) findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb_unable);
        CreateAccoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctormap.put("D_full_name", editTexts[0].getText().toString());
                Doctormap.put("Email", editTexts[1].getText().toString());
                Doctormap.put("Check_Email", editTexts[2].getText().toString());
                Doctormap.put("Password", editTexts[3].getText().toString());
                Doctormap.put("Mobile_Phone", editTexts[4].getText().toString());
                Doctormap.put("Birthday", editTexts[5].getText().toString());
                if (radioButtons[0].isChecked()) {
                    Doctormap.put("D_Gender", radioButtons[0].getText().toString());
                } else if (radioButtons[1].isChecked()) {
                    Doctormap.put("D_Gender", radioButtons[1].getText().toString());
                }
                if (radioButtons[2].isChecked()) {
                    Doctormap.put("D_able", radioButtons[2].getText().toString());
                } else if (radioButtons[3].isChecked()) {
                    Doctormap.put("D_able", radioButtons[3].getText().toString());
                }
            }
        });
    }
    public void This(View view) {
        Intent intent = new Intent(this,main_activity.class);
        startActivity(intent);
    }
}