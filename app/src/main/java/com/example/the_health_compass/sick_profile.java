package com.example.the_health_compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class sick_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String UserNameX, UserEmailX, BirthdayX, GenderX, MobilePhoneX, CheckEmailX;
    TextView UserName, UserEmail, Birthday, Gender, MobilePhone, CheckEmail;
    Bitmap Image;
    DrawerLayout drawer;
    private int counttouch = 0;
    ArrayList<String> rolev;
    ImageView imageViewProfile;
    Button btn_Open_Edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_profile);
        imageViewProfile = (ImageView)findViewById(R.id.imageView7);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        UserName = (TextView) findViewById(R.id.tv_full_name);
        UserEmail = (TextView) findViewById(R.id.tv_Email);
        Birthday = (TextView) findViewById(R.id.tv_birthday);
        Gender = (TextView) findViewById(R.id.tv_gender);
        MobilePhone = (TextView) findViewById(R.id.tv_Phone_Mobile_Sick);
        CheckEmail = (TextView) findViewById(R.id.tv_check_email);
        if (ReadXML_Profile()) {
            UserName.setText(UserNameX);
            UserEmail.setText(UserEmailX);
            Birthday.setText(BirthdayX);
            Gender.setText(GenderX);
            MobilePhone.setText(MobilePhoneX);
            CheckEmail.setText(CheckEmailX);
        }
        btn_Open_Edit = (Button) findViewById(R.id.btn_edit_profile);
        btn_Open_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_Edit_Profile();
            }
        });
    }

    public boolean ReadXML_Profile() {
        rolev = new ArrayList<String>();
        Document document;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
            File file = new File(filePath);
            document = documentBuilder.parse(file);
            Element element = document.getDocumentElement();
            UserNameX = getTextValue(UserNameX, element, "S_Full_Name");
            rolev.add(UserNameX);
            UserEmailX = getTextValue(UserEmailX, element, "Email");
            rolev.add(UserEmailX);
            BirthdayX = getTextValue(BirthdayX, element, "S_Birthday");
            rolev.add(BirthdayX);
            GenderX = getTextValue(GenderX, element, "S_Gender");
            rolev.add(GenderX);
            MobilePhoneX = getTextValue(MobilePhoneX, element, "Phone_Mobile");
            rolev.add(MobilePhoneX);
            CheckEmailX = getTextValue(CheckEmailX, element, "Check_Email");
            rolev.add(CheckEmailX);
            return true;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home_page:
                intent = new Intent(this, main_activity.class);
                startActivity(intent);
                break;
            case R.id.medical_advice:
                intent = new Intent(this, medical_advice.class);
                startActivity(intent);
                break;
            case R.id.consult_house:
                intent = new Intent(this, consult_house.class);
                startActivity(intent);
                break;
            case R.id.notification:
                intent = new Intent(this, Notification_Page.class);
                startActivity(intent);
                break;
            case R.id.profile:
                break;
            case R.id.settings:
                intent = new Intent(this, Settings_page.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (counttouch == 0) {
            UserName = (TextView) findViewById(R.id.tv_nav_UserName);
            UserEmail = (TextView) findViewById(R.id.tv_nav_UserEmail);
            boolean ReadXml = ReadXML_Profile();
            if (ReadXml) {
                UserName.setText(UserNameX);
                UserEmail.setText(UserEmailX);
            } else {

            }
            counttouch++;
        }
        return super.dispatchTouchEvent(ev);
    }
    private static String getTextValue(String def, Element doc, String tag) {
        String value = def;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }

    public void Open_Edit_Profile() {
        Intent intent = new Intent(this,Edit_Profile_Sick.class);
        startActivity(intent);
    }
}
