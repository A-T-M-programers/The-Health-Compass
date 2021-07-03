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
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.DocumentsContract;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
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
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class main_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "main_activity";

    private int counttouch = 0;
    private DrawerLayout drawer;
    private Button open_, btn_Sign_in_3, btn_Sign_up_1;
    TextView UserName, UserEmail;
    String UserNameX, UserEmailX,User,User_ID;
    ArrayList<String> rolev;
    boolean Admin = false;
    File file;
    boolean x;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (Admin) {
            Intent intent = new Intent(this, Control_Panel_Page.class);
            startActivity(intent);
            finish();
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);


            btn_Sign_in_3 = (Button) findViewById(R.id.btn_Sign_IN_3);
            btn_Sign_up_1 = (Button) findViewById(R.id.btn_Sign_up_1);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            if (!readXML()) {
                btn_Sign_in_3.setVisibility(View.VISIBLE);
                btn_Sign_up_1.setVisibility(View.VISIBLE);
            }
        }

        //getNotification();
        workManager();

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
        file = new File(this.getFilesDir().getPath().toString() + "/Sick.xml");
        switch (item.getItemId()) {
            case R.id.home_page:
                break;
            case R.id.medical_advice:
                if (file.exists()) {
                    intent = new Intent(this, medical_advice.class);
                } else {
                    intent = new Intent(this, medical_advice_doctor.class);
                }
                intent.putExtra("User",User);
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
                if (file.exists()) {
                    intent = new Intent(this, sick_profile.class);
                } else {
                    intent = new Intent(this, Doctor_Profile.class);
                }
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this, Settings_page.class);
                startActivity(intent);
                break;
            case R.id.logout:
                DeleteXML();
                intent = new Intent(this,sing_in.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Admin) {
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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
            file = new File(this.getFilesDir().toString() + "/Sick.xml");
            if (file.exists()) {
                User = "Sick";
                dom = db.parse(file);
                Element doc = dom.getDocumentElement();
                UserNameX = getTextValue(UserNameX, doc, "Full_Name");

                if (UserNameX != null) {
                    if (!UserNameX.isEmpty())
                        rolev.add(UserNameX);
                }
                UserEmailX = getTextValue(UserEmailX, doc, "Email");
                if (UserEmailX != null) {
                    if (!UserEmailX.isEmpty())
                        rolev.add(UserNameX);
                }
                User_ID = getTextValue(UserEmailX, doc, "ID");
                if (User_ID != null) {
                    if (!User_ID.isEmpty())
                        rolev.add(User_ID);
                }
            } else {
                User = "Doctor";
                String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";
                file = new File(filePath);
                dom = db.parse(file);
                Element doc = dom.getDocumentElement();
                UserNameX = getTextValue(UserNameX, doc, "Full_Name");
                if (UserNameX != null) {
                    if (!UserNameX.isEmpty())
                        rolev.add(UserNameX);
                }
                UserEmailX = getTextValue(UserEmailX, doc, "Email");
                if (UserEmailX != null) {
                    if (!UserEmailX.isEmpty())
                        rolev.add(UserNameX);
                }
                User_ID = getTextValue(UserEmailX, doc, "ID");
                if (User_ID != null) {
                    if (!User_ID.isEmpty())
                        rolev.add(User_ID);
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

    public void DeleteXML() {
        if(file.exists())
        {
            file.delete();
        }
    }
    public void getNotification(){
        ArrayList<Diagnose_S_D> arrayList = new ArrayList<>();
        if (User.equals("Sick")){
            arrayList = new DataAccessLayer().getDiagnos_S_D("Sick_ID",User_ID,"Doctor");
        }else if (User.equals("Doctor")){
            arrayList = new DataAccessLayer().getDiagnos_S_D("Doctor_ID",User_ID,null);
            arrayList.addAll(new DataAccessLayer().getDiagnos_S_D("Doctor_ID",User_ID,"Sick"));
        }
        else {

        }
        for (int i = 0 ; i<arrayList.size();i++) {
            Notifications.showNotification(main_activity.this,"أستشارة طبية","لديك استشارة طبية قيد الأستجابة يمكنك الرد عليها بالضغط على الأشعار",User);
        }
    }
    private void workManager() {


        // Passing data to our works
        Data myData = new Data.Builder()
                .putString("Tile","أستشارة طبية" )
                .putString("Body", "لديك استشارة طبية قيد الأستجابة يمكنك الرد عليها بالضغط على الأشعار")
                .putString("User", User)
                .putString("User_ID", User_ID)
                .build();


        // This gonna run your work right away.
        // OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyJobManager.class).build();

        // Constraints for our work
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

//        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Notifications.TimerWithWorkManager.class)
//                .setInputData(myData)
//                .setConstraints(constraints)
//                .addTag("MY_WORK_MANAGER_TAG_ONE_TIME")
//                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(Notifications.TimerWithWorkManager.class, 0, TimeUnit.SECONDS,0,TimeUnit.SECONDS)
                .setInputData(myData)
                .setConstraints(constraints)
                .addTag("MY_WORK_MANAGER_PERIODIC_TAG")
                .build();

        // Send our work to be scheduled.
//        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().enqueue(periodicWorkRequest);


        // Canceling our jobs by their Id
        // Get Id for our jobs (after we enqueue them)

//        UUID oneTimeWorkRequestId = oneTimeWorkRequest.getId();
//        WorkManager.getInstance().cancelWorkById(oneTimeWorkRequestId);

        UUID periodicWorkRequestId = periodicWorkRequest.getId();
//        WorkManager.getInstance().cancelWorkById(periodicWorkRequestId);



        // Get result from work manager
//        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequestId).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(@Nullable WorkInfo workInfo) {
//                if (workInfo != null && workInfo.getState().isFinished()){
//                    String result = workInfo.getOutputData().getString("Tile")+workInfo.getOutputData().getString("Body")+workInfo.getOutputData().getString("User");
//                    Log.d(TAG, "onChanged: " + result);
//                }
//            }
//        });
    }
}
