package com.example.the_health_compass;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.concurrent.Executor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int counttouch = 0;
    private DrawerLayout drawer;
    private Button open_, btn_Sign_in_3, btn_Sign_up_1;
    TextView UserName, UserEmail;
    String UserNameX, UserEmailX;
    ArrayList<String> rolev;
    boolean x;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

/*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_page()).commit();
            navigationView.setCheckedItem(R.id.home_page);
        }
*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Sign_IN_3) {
            OpenSignIn();
        } else if (v.getId() == R.id.btn_Sign_up_1) {
            OpenSignUp();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        btn_Sign_in_3 = (Button) findViewById(R.id.btn_Sign_IN_3);
        btn_Sign_in_3.setOnClickListener(this);
        btn_Sign_up_1 = (Button) findViewById(R.id.btn_Sign_up_1);
        btn_Sign_up_1.setOnClickListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
           case R.id.home_page:
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
                intent =new Intent(this,sick_profile.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this,Settings_page.class);
                startActivity(intent);
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

    public void OpenSignIn() {
        Intent intent = new Intent(this, sing_in.class);
        startActivity(intent);
    }

    public void OpenSignUp() {
        Intent intent = new Intent(this, sing_up_sick.class);
        startActivity(intent);
    }

    NotificationManager manager;
    static int notID = 1;

    public void buclick(View view) {
        NotificationCompat.Builder rold = new NotificationCompat.Builder(this);
        rold.setContentTitle("Danger");
        rold.setContentTitle("تم الارسال بنجاح");
        //rold.setSmallIcon();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        manager.notify(1, rold.build());
        notID++;

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
            String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";

            //String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";

            File f = new File(filePath);
            dom = db.parse(f);

            Element doc = dom.getDocumentElement();
            UserNameX = getTextValue(UserNameX, doc, "S_Full_Name");
            //UserNameX = getTextValue(UserNameX, doc, "D_Full_Name");

            //UserNameX += " " + getTextValue(UserNameX, doc, "S_Last_Name");

            if (UserNameX != null) {
                if (!UserNameX.isEmpty())
                    rolev.add(UserNameX);
            }
            UserEmailX = getTextValue(UserEmailX, doc, "Email");
            //UserEmailX = getTextValue(UserEmailX, doc, "D_Email");
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


}
