package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class sing_in extends AppCompatActivity {
    private TextView create_account ;
    Button btn_Sign_in;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btn_Sign_in = (Button)findViewById(R.id.btn_sign_in) ;
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.ed_user_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.ed_password,".{6,}", R.string.invalid_password);

        btn_Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"بيانات صحيحة",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"بيانات خاطئة",Toast.LENGTH_SHORT).show();
                }
            }
        });
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