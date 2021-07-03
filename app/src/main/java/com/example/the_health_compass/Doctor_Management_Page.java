package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;

public class Doctor_Management_Page extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    TextView []textViews= new TextView[3];
    EditText [] editTexts= new EditText[8];
    Button [] buttons = new Button[4];
    Spinner Subscription;
    RadioGroup [] radioGroups = new RadioGroup[2];
    RadioButton [] radioButtons = new RadioButton[4];
    Spinner subscription ;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    HashMap<String,String> DoctorMap = new HashMap<String, String>();
    Doctor D = new Doctor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_management_page);

        subscription= (Spinner)findViewById(R.id.sp_subscription);

        textViews[0] = (TextView)findViewById(R.id.tv_subscription2);
        textViews[1] = (TextView)findViewById(R.id.tv_gender3);
        textViews[2] = (TextView)findViewById(R.id.tv_gender2);

        radioGroups[0] = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroups[1] = (RadioGroup)findViewById(R.id.radioGroup2);


        radioButtons[0] = (RadioButton)findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton)findViewById(R.id.rb_female);
        radioButtons[2] = (RadioButton)findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton)findViewById(R.id.rb_unable);

        subscription = (Spinner)findViewById(R.id.sp_subscription);

        buttons[0] = (Button)findViewById(R.id.btn_add);
        buttons[1] = (Button)findViewById(R.id.btn_remove);
        buttons[2] = (Button)findViewById(R.id.btn_save_changes);
        buttons[3] = (Button)findViewById(R.id.btn_remove_Doctor_by_id);


        editTexts[0] = (EditText)findViewById(R.id.et_full_name);
        editTexts[1] = (EditText)findViewById(R.id.et_email);
        editTexts[2] = (EditText)findViewById(R.id.et_check_email);
        editTexts[3] = (EditText)findViewById(R.id.et_password);
        editTexts[4] = (EditText)findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText)findViewById(R.id.ed_birthday);
        editTexts[6] = (EditText)findViewById(R.id.ed_config_password);
        editTexts[7] = (EditText)findViewById(R.id.ed_ID);





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

                editTexts[7].setVisibility(View.INVISIBLE);
                buttons[3].setVisibility(View.INVISIBLE);

                editTexts[0].setVisibility(View.VISIBLE);
                editTexts[1].setVisibility(View.VISIBLE);
                editTexts[2].setVisibility(View.VISIBLE);
                editTexts[3].setVisibility(View.VISIBLE);
                editTexts[4].setVisibility(View.VISIBLE);
                editTexts[5].setVisibility(View.VISIBLE);
                editTexts[6].setVisibility(View.VISIBLE);

                buttons[2].setVisibility(View.VISIBLE);

                textViews[0].setVisibility(View.VISIBLE);
                textViews[1].setVisibility(View.VISIBLE);
                textViews[2].setVisibility(View.VISIBLE);

                radioGroups[0].setVisibility(View.VISIBLE);
                radioGroups[1].setVisibility(View.VISIBLE);

                subscription.setVisibility(View.VISIBLE);



            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTexts[0].setVisibility(View.INVISIBLE);
                editTexts[1].setVisibility(View.INVISIBLE);
                editTexts[2].setVisibility(View.INVISIBLE);
                editTexts[3].setVisibility(View.INVISIBLE);
                editTexts[4].setVisibility(View.INVISIBLE);
                editTexts[5].setVisibility(View.INVISIBLE);
                editTexts[6].setVisibility(View.INVISIBLE);

                buttons[2].setVisibility(View.INVISIBLE);

                textViews[0].setVisibility(View.INVISIBLE);
                textViews[1].setVisibility(View.INVISIBLE);
                textViews[2].setVisibility(View.INVISIBLE);

                radioGroups[0].setVisibility(View.INVISIBLE);
                radioGroups[1].setVisibility(View.INVISIBLE);

                subscription.setVisibility(View.INVISIBLE);


                editTexts[7].setVisibility(View.VISIBLE);
                buttons[3].setVisibility(View.VISIBLE);
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
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAccessLayer.deleteDoctor(Integer.parseInt(editTexts[7].getText().toString()));
                Toast.makeText(getApplicationContext(),"الطبيب رقم "+editTexts[7].getText().toString()+" تم حذفه",Toast.LENGTH_SHORT).show();
            }
        });
    }
}