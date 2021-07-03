package com.example.the_health_compass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_health_compass.SendNotification.APIService;
import com.example.the_health_compass.SendNotification.Client;
import com.example.the_health_compass.SendNotification.Data;
import com.example.the_health_compass.SendNotification.MyResponse;
import com.example.the_health_compass.SendNotification.NotificationSender;
import com.example.the_health_compass.SendNotification.Token;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class medical_advice_doctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private int counttouch = 0;
    TextView UserName, UserEmail;
    String UserNameX, UserEmailX, UserIDX,User;
    ArrayList<String> rolev;
    File file;

    String U,T,token;
    private APIService apiService;

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
        Intent intent  = getIntent();
        if (intent.hasExtra("Type")){
            U = intent.getExtras().getString("User");
            T = intent.getExtras().getString("Type");
            token = intent.getExtras().getString("Token");
            fullDiagnos(U,T);
        }
        else if (intent.hasExtra("User")) {
            String token = intent.getExtras().getString("Token");
            T = "Sick";
            fullDiagnos(intent.getExtras().getString("User")+"_ID",T);
        }


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

                UserIDX = getTextValue(UserEmailX, doc, "ID");
                if (UserIDX != null) {
                    if (!UserIDX.isEmpty())
                        rolev.add(UserIDX);
                }
                User = "Sick_ID";

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
                User = "Doctor_ID";
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
                if (User.equals("Sick_ID")){
                    intent = new Intent(this, medical_advice.class);
                    startActivity(intent);
                }else if (User.equals("Doctor")){

                }
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

    private void fullDiagnos(String User,String Type) {
        this.User = User;
        boolean x= readXML();
        ArrayList<Diagnose_S_D> diagnose_s_dArrayList = dataAccessLayer.getDiagnos_S_D(User,UserIDX,Type);
        diagnos = dataAccessLayer.getDiagnos(diagnose_s_dArrayList);
        if (Type.equals("Doctor")) {
            buildRecyclerView("Sick");
        }else if (Type.equals("Sick")){
            buildRecyclerView("Doctor");
        }

    }

    private void buildRecyclerView(String Type) {
        Diagnos = findViewById(R.id.Diagnos);
        Diagnos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Recycler_Diagnos(Type,diagnos, new Recycler_Diagnos.ItemClickListener() {
            @Override
            public void onItemClick(ListDiagnos listDiagnos) {

            }
        });
        Diagnos.setLayoutManager(mLayoutManager);
        Diagnos.setAdapter(mAdapter);
    }
/*//    public void Excutenotification(String Type){
//        //FirebaseApp.initializeApp(itemView.getContext());
//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
//        FirebaseDatabase.getInstance().getReference().child("Tokens").child(Type.trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String usertoken = snapshot.getValue(String.class);
//                sendNotifications(usertoken,"أستشارة طبية", "لديك استشارة طبية قيد الأستجابة يمكنك الرد عليها بالضغط على الأشعار",Type);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        UpdateToken(Type);
//    }
//    public void sendNotifications(String usertoken,String title,String message,String user){
//        Data data = new Data(title,message,user,usertoken);
//        NotificationSender sender = new NotificationSender(data,usertoken);
//        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code()==200){
//                    if (response.body().success!=1){
//                        Toast.makeText(medical_advice_doctor.this, "Failed",Toast.LENGTH_LONG);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }
//    private void UpdateToken(String Type){
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken = FirebaseInstanceId.getInstance().getToken();
//        Token token = new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Tokens").child(Type).setValue(token);
//    }*/
}
