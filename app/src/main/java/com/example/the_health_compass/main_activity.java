package com.example.the_health_compass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.nio.file.OpenOption;

public class main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Button sing_in_button;
    Button Sign_In;
    static TextView UserName,UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setActivated(true);
        UserName = (TextView)findViewById(R.id.tv_nav_UserName);
        UserEmail = (TextView)findViewById(R.id.tv_nav_UserEmail);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_page()).commit();
            navigationView.setCheckedItem(R.id.home_page);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_page()).commit();
                break;
            case R.id.medical_advice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new medical_advice()).commit();
                break;
            case R.id.consult_house:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new consult_house()).commit();
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new sick_profile()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void OpenSickIn(){
        Intent intent = new Intent(this,sing_up_sick.class);
        startActivity(intent);
    }
    public static void Stored(Sick YourSick){
        UserName.setText(YourSick.S_First_Name+" "+YourSick.S_Last_Name);
        UserEmail.setText(YourSick.Email);
    }
}
