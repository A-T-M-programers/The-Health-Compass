package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;

public class Sick_Management_Page extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    Button [] buttons = new Button[3];
    EditText[] editTexts = new EditText[5];
    RadioButton []radioButtons= new RadioButton[2];
    HashMap<String,String> SickMap = new HashMap<String, String>(){};
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    Sick s = new Sick();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_management_page);
        buttons[0] = (Button)findViewById(R.id.btn_add_sick);
        buttons[1] = (Button)findViewById(R.id.btn_remove_sick);
        buttons[2] = (Button)findViewById(R.id.btn_save_changes);

        editTexts[0] = (EditText)findViewById(R.id.et_full_name);
        editTexts[1] = (EditText)findViewById(R.id.et_birthday);
        editTexts[2] = (EditText)findViewById(R.id.et_email);
        editTexts[3] = (EditText)findViewById(R.id.et_mobile_phone);
        editTexts[4] = (EditText)findViewById(R.id.ed_sick_password);

        radioButtons[0] = (RadioButton)findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton)findViewById(R.id.rb_female);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_full_name,"[a-zA-Zأ-ي\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.ed_sick_password,"[1-9a-zA-Z\\s]+",R.string.invalid_password2);
        awesomeValidation.addValidation(this, R.id.ed_sick_password, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.ed_confirm_password, R.id.et_password, R.string.invalid_confirm_password);
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);
        awesomeValidation.addValidation(this, R.id.et_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"بيانات صحيحة",Toast.LENGTH_SHORT).show();

                    SickMap.put("S_Full_Name",editTexts[0].getText().toString());
                    SickMap.put("Email",editTexts[0].getText().toString());
                    SickMap.put("Check_Email",editTexts[0].getText().toString());
                    SickMap.put("Password",editTexts[0].getText().toString());
                    SickMap.put("Phone_Mobile",editTexts[0].getText().toString());
                    SickMap.put("S_Birthday",editTexts[0].getText().toString());
                    if(radioButtons[0].isChecked()){
                        SickMap.put("S_Gender",radioButtons[0].getText().toString());
                    }
                    else{
                        SickMap.put("S_Gender",radioButtons[1].getText().toString());
                    }
                    s.InPutSick(SickMap);
                    s.InputShare(SickMap,false);
                    String CheckDataBase = dataAccessLayer.getsick(s.S_Full_Name, s.Email, s.Password);
                    //Check if Sink Found in DataBase
                    switch (CheckDataBase) {
                        case "User":
                            return;
                        case "Email":
                            return;
                        case "Password":
                            return;
                        default:
                            break;
                    }
                    // Add sick to database
                    boolean CheckSet = dataAccessLayer.SetSick(s);

                }
                else{
                    Toast.makeText(getApplicationContext(),"بيانات خاطئة",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}