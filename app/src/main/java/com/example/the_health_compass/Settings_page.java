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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class Settings_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private int counttouch = 0;
    TextView UserName, UserEmail;
    String UserNameX, UserEmailX;
    ArrayList<String> rolev;
    File file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
    @Override
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
    // Read XML File
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
            File file = new File(this.getFilesDir().toString()+"/Sick.xml");
            if(file.exists()) {
                String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
                File f = new File(filePath);
                dom = db.parse(f);

                Element doc = dom.getDocumentElement();
                UserNameX = getTextValue(UserNameX, doc, "S_Full_Name");

                if (UserNameX != null) {
                    if (!UserNameX.isEmpty())
                        rolev.add(UserNameX);
                }
                UserEmailX = getTextValue(UserEmailX, doc, "Email");
                if (UserEmailX != null) {
                    if (!UserEmailX.isEmpty())
                        rolev.add(UserNameX);
                }
            }
            else{
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
        file = new File(this.getFilesDir().getPath().toString()+"/Sick.xml");
        switch (item.getItemId()) {
            case R.id.home_page:
                intent = new Intent(this,main_activity.class);
                startActivity(intent);
                break;
            case R.id.medical_advice:
                intent = new Intent(this,medical_advice.class);
                startActivity(intent);
                break;
            case R.id.consult_house:
                if (file.exists()) {
                    intent = new Intent(this, consult_house.class);
                } else {
                    intent = new Intent(this, consult_house_doctor.class);
                }
                startActivity(intent);
                break;
            case R.id.notification:
                intent = new Intent(this, Notification_Page.class);
                startActivity(intent);
                break;
            case R.id.profile:

                if(file.exists()){
                    intent =new Intent(this,sick_profile.class);
                }else {
                    intent =new Intent(this,Doctor_Profile.class);
                }
                startActivity(intent);
                break;
            case R.id.settings:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}