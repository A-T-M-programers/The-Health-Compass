package com.example.the_health_compass;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class Doctor_Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    int counttouch=0;
    String UserNameX, UserEmailX, BirthdayX, GenderX, MobilePhoneX, CheckEmailX,SpecializationX;
    Matrix Image;
    TextView UserName, UserEmail, Birthday, Gender, MobilePhone, CheckEmail,Specialization;
    ArrayList<String> rolev;
    Button btn_Edit_Profile;
    ImageView imageViewProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        btn_Edit_Profile = (Button) findViewById(R.id.btn_sign_in2);
        imageViewProfile =(ImageView)findViewById(R.id.imageViewProfile);
        UserName  =(TextView)findViewById(R.id.tv_full_name);
        UserEmail = (TextView)findViewById(R.id.tv_Email);
        Birthday = (TextView)findViewById(R.id.tv_birthday);
        Gender = (TextView)findViewById(R.id.tv_gender);
        MobilePhone = (TextView)findViewById(R.id.tv_mobile_phone);
        CheckEmail = (TextView)findViewById(R.id.tv_check_email);
        Specialization = (TextView) findViewById(R.id.tv_sectionity);

        if (ReadXML_Profile()) {
            UserName.setText(UserNameX);
            UserEmail.setText(UserEmailX);
            Birthday.setText(BirthdayX);
            Gender.setText(GenderX);
            MobilePhone.setText(MobilePhoneX);
            CheckEmail.setText(CheckEmailX);
            Specialization.setText(SpecializationX);
            imageViewProfile.setImageMatrix(Image);
            /* Image Profile */

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        btn_Edit_Profile.setOnClickListener(new View.OnClickListener() {
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
            String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";
            File file = new File(filePath);
            document = documentBuilder.parse(file);
            Element element = document.getDocumentElement();
            UserNameX = getTextValue(UserNameX, element, "D_Full_Name");
            rolev.add(UserNameX);
            UserEmailX = getTextValue(UserEmailX, element, "D_Email");
            rolev.add(UserEmailX);
            BirthdayX = getTextValue(BirthdayX, element, "D_Birthday");
            rolev.add(BirthdayX);
            GenderX = getTextValue(GenderX, element, "D_Gender");
            rolev.add(GenderX);
            MobilePhoneX = getTextValue(MobilePhoneX, element, "Mobile_Phone");
            rolev.add(MobilePhoneX);
            CheckEmailX = getTextValue(CheckEmailX, element, "D_Check_Email");
            rolev.add(CheckEmailX);
            SpecializationX = getTextValue(SpecializationX,element,"D_Specialization");
            rolev.add(SpecializationX);
//            Image = getTextValue(Image,element,"D_Personal_Image");
//            rolev.add(Image);
            return true;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (counttouch == 0) {
            UserName = (TextView) findViewById(R.id.tv_nav_UserName);
            UserEmail = (TextView) findViewById(R.id.tv_nav_UserEmail);
            boolean ReadXml = readXML();
            if (ReadXml) {
                UserName.setText(UserNameX);
                UserEmail.setText(UserEmailX);
            } else {

            }
            counttouch++;
        }
        return super.dispatchTouchEvent(ev);
    }
    public boolean readXML() {
        rolev = new ArrayList<String>();
        Document dom;

        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            // parse using the builder to get the DOM mapping of the
            // XML file

            String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";

            File f = new File(filePath);
            dom = db.parse(f);

            Element doc = dom.getDocumentElement();
            UserNameX = getTextValue(UserNameX, doc, "D_Full_Name");

            if (UserNameX != null) {
                if (!UserNameX.isEmpty())
                    rolev.add(UserNameX);
            }
            UserEmailX = getTextValue(UserEmailX, doc, "D_Email");
            if (UserEmailX != null) {
                if (!UserEmailX.isEmpty())
                    rolev.add(UserNameX);
            }
            return true;

        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home_page:
                intent =new Intent(this,main_activity.class);
                startActivity(intent);
                break;
            case R.id.medical_advice:
                intent = new Intent(this,medical_advice.class);
                startActivity(intent);
                break;
            case R.id.consult_house:
                intent = new Intent(this,consult_house.class);
                startActivity(intent);
                break;
            case R.id.notification:
                intent = new Intent(this, Notification_Page.class);
                startActivity(intent);
                break;
            case R.id.profile:

                break;
            case R.id.settings:
                intent = new Intent(this,Settings_page.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void Open_Edit_Profile() {
        Intent intent = new Intent(this,Edit_Doctor_Profile.class);
        startActivity(intent);
    }
}
