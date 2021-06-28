package com.example.the_health_compass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class medical_advice_doctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private int counttouch = 0;
    TextView UserName, UserEmail;
    String UserNameX, UserEmailX, UserIDX;
    ArrayList<String> rolev;
    File file;

    private ArrayList<ListDiagnos> diagnos = new ArrayList<>();

    private RecyclerView Diagnos;
    private Recycler_Diagnos mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DataAccessLayer dataAccessLayer = new DataAccessLayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_advice_doctor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        fullDiagnos();


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
            File file = new File(this.getFilesDir().toString() + "/Sick.xml");
            if (file.exists()) {
                String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
                File f = new File(filePath);
                dom = db.parse(f);

                Element doc = dom.getDocumentElement();
                UserNameX = getTextValue(UserNameX, doc, "S_Full_Name");

                //UserNameX += " " + getTextValue(UserNameX, doc, "S_Last_Name");

                if (UserNameX != null) {
                    if (!UserNameX.isEmpty())
                        rolev.add(UserNameX);
                }
                UserEmailX = getTextValue(UserEmailX, doc, "Email");
                if (UserEmailX != null) {
                    if (!UserEmailX.isEmpty())
                        rolev.add(UserEmailX);
                }

            } else {
                String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";
                File f = new File(filePath);
                dom = db.parse(f);

                Element doc = dom.getDocumentElement();
                UserNameX = getTextValue(UserNameX, doc, "D_Full_Name");
                if (UserNameX != null) {
                    if (!UserNameX.isEmpty())
                        rolev.add(UserNameX);
                }
                UserEmailX = getTextValue(UserEmailX, doc, "Email");
                if (UserEmailX != null) {
                    if (!UserEmailX.isEmpty())
                        rolev.add(UserNameX);
                }

                UserIDX = getTextValue(UserEmailX, doc, "ID");
                if (UserIDX != null) {
                    if (!UserIDX.isEmpty())
                        rolev.add(UserIDX);
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
        switch (item.getItemId()) {
            case R.id.home_page:
                intent = new Intent(this, main_activity.class);
                startActivity(intent);
                break;
            case R.id.medical_advice:
                break;
            case R.id.consult_house:
                intent = new Intent(this, consult_house_doctor.class);
                startActivity(intent);
                break;
            case R.id.notification:
                intent = new Intent(this, Notification_Page.class);
                startActivity(intent);
                break;
            case R.id.profile:
                intent = new Intent(this, Doctor_Profile.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this, Settings_page.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void fullDiagnos() {
        boolean x= readXML();
        ArrayList<Diagnose_S_D> diagnose_s_dArrayList = dataAccessLayer.getDiagnos_S_D(UserIDX);
        diagnos = dataAccessLayer.getDiagnos(diagnose_s_dArrayList);
        buildRecyclerView();

    }

    private void buildRecyclerView() {
        Diagnos = findViewById(R.id.Diagnos);
        Diagnos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Recycler_Diagnos(diagnos, new Recycler_Diagnos.ItemClickListener() {
            @Override
            public void onItemClick(ListDiagnos listDiagnos) {

            }
        });
        Diagnos.setLayoutManager(mLayoutManager);
        Diagnos.setAdapter(mAdapter);
    }
}
