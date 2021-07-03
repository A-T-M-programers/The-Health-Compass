package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.the_health_compass.DataAccessLayer;
import com.example.the_health_compass.Sick;
import com.google.common.base.Converter;

import java.util.ArrayList;
import java.util.HashMap;

public class Sick_Management_Page extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    Button [] buttons = new Button[4];
    EditText[] editTexts = new EditText[8];
    RadioButton []radioButtons= new RadioButton[2];
    ArrayList<ListSick> sicks = new ArrayList<>();
    HashMap<String,String> SickMap = new HashMap<String, String>(){};
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    Sick s = new Sick();
    TextView Gender;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_management_page);
        //Textview
        Gender = (TextView)findViewById(R.id.tv_gender4);
        // radioGroup
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        // Buttons
        buttons[0] = (Button)findViewById(R.id.btn_add_sick);
        buttons[1] = (Button)findViewById(R.id.btn_remove_sick);
        buttons[2] = (Button)findViewById(R.id.btn_save_changes);
        buttons[3] = (Button)findViewById(R.id.btn_remove_sick_by_id);


        // editTexts
        editTexts[0] = (EditText)findViewById(R.id.et_full_name);
        editTexts[1] = (EditText)findViewById(R.id.et_email);
        editTexts[2] = (EditText)findViewById(R.id.et_check_email);

        editTexts[3] = (EditText)findViewById(R.id.et_mobile_phone);
        editTexts[4] = (EditText)findViewById(R.id.ed_sick_password);
        editTexts[5] = (EditText)findViewById(R.id.ed_confirm_password);
        editTexts[6] = (EditText)findViewById(R.id.et_birthday);
        editTexts[7] = (EditText)findViewById(R.id.ed_ID);

        //radioButtons
        radioButtons[0] = (RadioButton)findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton)findViewById(R.id.rb_female);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validate sick name
        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_full_name,"[a-zA-Zأ-ي\\s]+", R.string.invalid_name);

        // Validate sick email
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        // Validate sick password
        awesomeValidation.addValidation(this,R.id.ed_sick_password,"[1-9a-zA-Z\\s]+",R.string.invalid_password2);
        awesomeValidation.addValidation(this, R.id.ed_sick_password, ".{6,}", R.string.invalid_password);

        // Validate sick confirm password
        awesomeValidation.addValidation(this, R.id.ed_confirm_password, R.id.et_password, R.string.invalid_confirm_password);

        // Validate sick mobile phone number
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);

        // Validate birthday
        awesomeValidation.addValidation(this, R.id.et_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);

        String check = dataAccessLayer.getSicks(sicks);

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editTexts[7].setVisibility(View.INVISIBLE);
                buttons[3].setVisibility(View.INVISIBLE);

                // editTexts
                editTexts[0].setVisibility(View.VISIBLE);
                editTexts[1].setVisibility(View.VISIBLE);
                editTexts[2].setVisibility(View.VISIBLE);
                editTexts[3].setVisibility(View.VISIBLE);
                editTexts[4].setVisibility(View.VISIBLE);
                editTexts[5].setVisibility(View.VISIBLE);
                editTexts[6].setVisibility(View.VISIBLE);


                // Buttons
                buttons[2].setVisibility(View.VISIBLE);

                // textView
                Gender.setVisibility(View.VISIBLE);

                // radioGroup
                radioGroup.setVisibility(View.VISIBLE);
            }
        });
        editTexts[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBirthday(editTexts[6]);
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editTexts[7].setVisibility(View.VISIBLE);
                buttons[3].setVisibility(View.VISIBLE);

                editTexts[0].setVisibility(View.INVISIBLE);
                editTexts[1].setVisibility(View.INVISIBLE);
                editTexts[2].setVisibility(View.INVISIBLE);
                editTexts[3].setVisibility(View.INVISIBLE);
                editTexts[4].setVisibility(View.INVISIBLE);
                editTexts[5].setVisibility(View.INVISIBLE);
                editTexts[6].setVisibility(View.INVISIBLE);




                // Buttons
                buttons[2].setVisibility(View.INVISIBLE);

                // textView
                Gender.setVisibility(View.INVISIBLE);

                // radioGroup
                radioGroup.setVisibility(View.INVISIBLE);
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"بيانات صحيحة",Toast.LENGTH_SHORT).show();

                    // Add data to Hashmap
                    SickMap.put("S_Full_Name",editTexts[0].getText().toString());
                    SickMap.put("Email",editTexts[1].getText().toString());
                    SickMap.put("Check_Email",editTexts[2].getText().toString());
                    SickMap.put("Password",editTexts[4].getText().toString());
                    SickMap.put("Phone_Mobile",editTexts[3].getText().toString());
                    SickMap.put("S_Birthday",editTexts[6].getText().toString());
                    if(radioButtons[0].isChecked()){
                        SickMap.put("S_Gender",radioButtons[0].getText().toString());
                    }
                    else{
                        SickMap.put("S_Gender",radioButtons[1].getText().toString());
                    }
                    // Add data to sick object
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
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAccessLayer.deleteSick(Integer.parseInt(editTexts[7].getText().toString()));
                Toast.makeText(getApplicationContext(),"المريض رقم "+editTexts[7].getText().toString()+" تم حذفه",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ShowDialogBirthday(EditText editText) {
        DialogFragment newFragment = new DatePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }
}