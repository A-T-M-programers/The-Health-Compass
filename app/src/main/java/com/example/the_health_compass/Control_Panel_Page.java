package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Control_Panel_Page extends AppCompatActivity {

    CardView[] cardViews = new CardView[5];
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel_page);
        cardViews[0] = (CardView)findViewById(R.id.ad_manager);
        cardViews[1] = (CardView)findViewById(R.id.sick_manager);
        cardViews[2] = (CardView)findViewById(R.id.medical_advice_manager);
        cardViews[3] = (CardView)findViewById(R.id.doctor_manager);
        cardViews[4] = (CardView)findViewById(R.id.hospital_manager);


        cardViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Ad_Manager();
            }
        });
        cardViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Sick_Manager();
            }
        });
        cardViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Medical_Advice_Manager();
            }
        });
        cardViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Doctor_Manager();
            }
        });
        cardViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Hospital_Manager_Page();
            }
        });
    }
    public void Open_Ad_Manager(){
        intent = new Intent(this,Adivertisement_Management_Page.class);
        startActivity(intent);
    }
    public void Open_Sick_Manager(){
        intent = new Intent(this,Sick_Management_Page.class);
        startActivity(intent);
    }
    public void Open_Doctor_Manager(){
        intent = new Intent(this,Doctor_Management_Page.class);
        startActivity(intent);
    }
    public void Open_Medical_Advice_Manager(){
        intent = new Intent(this,Medical_Advice_Management_Page.class);
        startActivity(intent);
    }
    public void  Open_Hospital_Manager_Page(){
        intent = new Intent(this,Hospital_Management_Page.class);
        startActivity(intent);
    }
}