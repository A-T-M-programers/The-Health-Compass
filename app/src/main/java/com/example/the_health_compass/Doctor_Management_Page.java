package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;

public class Doctor_Management_Page extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    EditText [] editTexts= new EditText[6];
    Button [] buttons = new Button[3];
    RadioButton [] radioButtons = new RadioButton[4];
    Spinner subscription ;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    HashMap<String,String> DoctorMap = new HashMap<String, String>();
    Doctor D = new Doctor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_management_page);

        radioButtons[0] = (RadioButton)findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton)findViewById(R.id.rb_female);
        radioButtons[2] = (RadioButton)findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton)findViewById(R.id.rb_unable);

        subscription = (Spinner)findViewById(R.id.sp_subscription);

        buttons[0] = (Button)findViewById(R.id.btn_add);
        buttons[1] = (Button)findViewById(R.id.btn_remove);
        buttons[2] = (Button)findViewById(R.id.btn_save_changes);

        editTexts[0] = (EditText)findViewById(R.id.et_full_name);
        editTexts[1] = (EditText)findViewById(R.id.et_email);
        editTexts[2] = (EditText)findViewById(R.id.et_check_email);
        editTexts[3] = (EditText)findViewById(R.id.et_password);
        editTexts[4] = (EditText)findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText)findViewById(R.id.et_birthday);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_full_name,"[a-zA-Zأ-ي\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_check_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.et_password,"[1-9a-zA-Z\\s]+",R.string.invalid_password2);
        awesomeValidation.addValidation(this, R.id.et_password, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.ed_config_password, R.id.et_password, R.string.invalid_confirm_password);
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);
        awesomeValidation.addValidation(this, R.id.ed_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);



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
                    Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();

                    DoctorMap.put("D_Full_Name", editTexts[0].getText().toString());
                    DoctorMap.put("D_Email", editTexts[1].getText().toString());
                    DoctorMap.put("D_Check_Email", editTexts[2].getText().toString());
                    DoctorMap.put("D_Password", editTexts[3].getText().toString());
                    DoctorMap.put("D_Mobile_Phone", editTexts[4].getText().toString());
                    DoctorMap.put("D_Birthday", editTexts[5].getText().toString());
                    DoctorMap.put("D_Specialization", subscription.getSelectedItem().toString());

                    if (radioButtons[0].isChecked()) {
                        DoctorMap.put("D_Gender", radioButtons[0].getText().toString());
                    } else if (radioButtons[1].isChecked()) {
                        DoctorMap.put("D_Gender", radioButtons[1].getText().toString());
                    }
                    if (radioButtons[2].isChecked()) {
                        DoctorMap.put("D_Able", radioButtons[2].getText().toString());
                    } else if (radioButtons[3].isChecked()) {
                        DoctorMap.put("D_Able", radioButtons[3].getText().toString());
                    }
                    D.InputDoctor(DoctorMap);
                    D.InputShareDoctor(DoctorMap, false);
                    String CheckDataBase = dataAccessLayer.getDoctor(D.D_Full_Name, D.Email, D.Password);
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
                    boolean CheckSet = dataAccessLayer.SetDoctor(D);
                }
                else{
                    Toast.makeText(getApplicationContext(), "بيانات خاطئة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}