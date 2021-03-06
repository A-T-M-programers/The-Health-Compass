package com.example.the_health_compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArrayMap;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.common.base.Predicates;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class medical_advice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private int counttouch = 0;
    String User;
    HashMap<String,String> rolev;

    private ArrayList<ListDoctor> Doctors = new ArrayList<>();
    private ArrayList<String> Illness = new ArrayList<>();

    private RecyclerView Serching;
    private RecyclSerching mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DrawerLayout drawer;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    ImageButton Search_Doctors;
    EditText editTexts;
    TextView[] textView = new TextView[3];
    Spinner ilness;
    Button Send ;
    File file;

    TextView UserName, UserEmail;
    String UserNameX, UserEmailX;

    The_Diagnose the_diagnose ;
    String IDDoctor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_advice);

        editTexts = (EditText)findViewById(R.id.sv_search);
        Serching = (RecyclerView) findViewById(R.id.Searching);
        Search_Doctors = (ImageButton)findViewById(R.id.btn_Search);
        ilness = (Spinner)findViewById(R.id.Spinner_Illness);
        textView[0] = (TextView)findViewById(R.id.tv_disease_name);
        textView[1] = (TextView)findViewById(R.id.tv_disease_age);
        textView[2] = (TextView)findViewById(R.id.tv_doctor_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        String CheckDoctor = dataAccessLayer.getDoctors(Doctors);
        buildRecyclerView();

        String CheckIlness = dataAccessLayer.getIllnessName(Illness);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(medical_advice.this,
                R.layout.support_simple_spinner_dropdown_item,
                Illness);
        ilness.setAdapter(arrayAdapter);


        editTexts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
                Serching.setVisibility(View.VISIBLE);
                if (s.length()==0){
                    Serching.setVisibility(View.INVISIBLE);
                }
            }
        });
        boolean check = readXML();
        Send = (Button)findViewById(R.id.btn_sent_medical_advice);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartOfBody();
            }
        });

    }

    private void filter(String text) {
        ArrayList<ListDoctor> filteredList = new ArrayList<>();

        for (ListDoctor item : Doctors) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
                IDDoctor = item.getID();
            }
        }

        mAdapter.filterList(filteredList);
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
                intent = new Intent(this,main_activity.class);
                startActivity(intent);
                break;
            case R.id.medical_advice:
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
                intent = new Intent(this, sick_profile.class);
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
    private void buildRecyclerView() {
        Serching = findViewById(R.id.Searching);
        Serching.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclSerching(Doctors, new RecyclSerching.ItemClickListener1() {
            @Override
            public void onItemClick(ListDoctor listDoctor) {
                textView[2].setText(listDoctor.getText1());
                editTexts.setText(listDoctor.getText1());
                Serching.setVisibility(View.INVISIBLE);
            }
        });

        Serching.setLayoutManager(mLayoutManager);
        Serching.setAdapter(mAdapter);
    }
    public boolean readXML() {
        rolev = new HashMap<String,String>();
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


            file = new File(filePath);
            dom = db.parse(file);

            Element doc = dom.getDocumentElement();

            User = getTextValue(User, doc, "Full_Name");

            UserNameX = getTextValue(UserNameX,doc,"Full_Name");
            UserEmailX = getTextValue(UserEmailX,doc,"Email");

            if (User != null) {
                if (!User.isEmpty())
                    textView[0].setText(User);
                    rolev.put("S_Full_Name",User);
            }
            User = getTextValue(User, doc, "Age");

            if (User != null) {
                if (!User.isEmpty())
                    textView[1].setText(User);
                    rolev.put("Age",User);
            }

            User = getTextValue(User, doc, "ID");

            if (User != null) {
                if (!User.isEmpty())
                    textView[1].setText(User);
                rolev.put("ID",User);
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
    public void PartOfBody(){
        ArrayMap<String,String> partofbody = new ArrayMap<>();
        String check = dataAccessLayer.getPartOfBody(partofbody);
        String[] item;
        item = new String[partofbody.size()];
        String[] key = new String[partofbody.size()];
        int x = 0;
        for (String s:partofbody.values()) {
            item[x]=s;
            key[x] = partofbody.keyAt(x);
            x++;
            if (x==partofbody.size()){
                x=0;
            }
        }
        boolean[] checked = new boolean[item.length];
        List<String> selected = Arrays.asList(item);
        AlertDialog.Builder builder = new AlertDialog.Builder(medical_advice.this);
        builder.setTitle(" ???????????? ?????????? ???????? ??????????");
        builder.setMultiChoiceItems(item, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which]=isChecked;
                PartOfBodyStyle(key[which]);
                dialog.cancel();
                //String current = selected.get(which);
            }
        });
        //builder.setCancelable(false);

        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void PartOfBodyStyle(String PartOfBodyID){
        ArrayMap<String,String> partofbodystyle =  new ArrayMap<>();
        String check = dataAccessLayer.getPartOfBodysyle(partofbodystyle,PartOfBodyID);
        String[] item;
        item = new String[partofbodystyle.size()];
        String [] key = new String[partofbodystyle.size()];
        int x = 0;
        for (String s:partofbodystyle.values()) {
            key[x]=partofbodystyle.keyAt(x);
            item[x] = s;
            x++;
            if (x==partofbodystyle.size()){
                x=0;
            }
        }
        boolean[] checked = new boolean[item.length];
        List<String> selected = Arrays.asList(item);
        AlertDialog.Builder builder = new AlertDialog.Builder(medical_advice.this);
        builder.setTitle("???? ?????????? ?????????? ?????? ?????????? ???? ?????? ??????????????????");
        builder.setMultiChoiceItems(item, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which]=isChecked;
                Syndrome_IL(key[which],PartOfBodyID);
                dialog.cancel();
            }
        });
        builder.setCancelable(false);

        // handle the negative button of the alert dialog
        builder.setNegativeButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PartOfBody();
            }
        });

        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void Syndrome_IL(String PartOfBodyStyleID,String PartOfBodyID){
        ArrayMap<String,String> syndormeil =  new ArrayMap<>();
        String check = dataAccessLayer.getSyndromeIl(syndormeil,PartOfBodyStyleID);
        String[] item;
        item = new String[syndormeil.size()];
        String [] key = new String[syndormeil.size()];
        int x = 0;
        for (String s:syndormeil.values()) {
            key[x]=syndormeil.keyAt(x);
            item[x] = s;
            x++;
            if (x==syndormeil.size()){
                x=0;
            }
        }
        boolean[] checked = new boolean[item.length];
        List<String> selected = Arrays.asList(item);
        AlertDialog.Builder builder = new AlertDialog.Builder(medical_advice.this);
        builder.setTitle("???? ???? ?????? ?????????????? ???????? ?????? :");
        builder.setMultiChoiceItems(item, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which]=isChecked;
                String current = selected.get(which);
            }
        });
        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("??????????", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> keysyndrome = new ArrayList<>();
                boolean check = false;
                for (int i = 0; i < checked.length; i++) {
                    if (checked[i]) {
                        check = keysyndrome.add(key[i]);
                    }
                }
                if (check){
                    Diagnos(PartOfBodyStyleID,PartOfBodyID,keysyndrome);
                }
            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PartOfBodyStyle(PartOfBodyID);
            }
        });

        // handle the neutral button of the dialog to clear
        // the selected items boolean checkedItem

        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void Diagnos(String PartOfBodyStyleID,String PartofbodyID,ArrayList<String> keysyndrome){
        final EditText editText = new EditText(this);
        ArrayMap<String, Integer> diagnos =  new ArrayMap<>();
        String[] DesDiagnos = dataAccessLayer.getDiagnos(diagnos,PartOfBodyStyleID,PartofbodyID,keysyndrome);
        AlertDialog.Builder builder = new AlertDialog.Builder(medical_advice.this);
        builder.setTitle("???? ???????????? ???? ?????????????? ???????? ???????????? ?????????????? :");
        if (null != DesDiagnos[1] & !DesDiagnos[1].equals("") & DesDiagnos[2].toLowerCase().equals("yes"))
        {
            builder.setMessage(DesDiagnos[1]+"\n"+"???????? ?????????????? ???????????? ???????????? :"+"\n"+textView[2].getText());
        }else if (DesDiagnos[1] != null & !DesDiagnos[1].equals("") & DesDiagnos[2].toLowerCase().equals("no")) {
            builder.setMessage(DesDiagnos[1]);
        }else  {
            builder.setMessage("?????????????? ???? ?????????? \n ???? ?????? ?????????? ?????????? ???????? ???????? ?????? ?????????? ?????????? ?????? ???????????? ???????????? : \n"+textView[2].getText());
        }


        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("??????????", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int sec = c.get(Calendar.SECOND);
                String Date1 = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day)+" "+String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(sec);
                String[] data = new String[]{DesDiagnos[0],IDDoctor,PartofbodyID,PartOfBodyStyleID,Date1};
                String[] Il = new String[keysyndrome.size()];
                the_diagnose = new The_Diagnose(data,keysyndrome.toArray(Il));

                AlertDialog.Builder builder2 = new AlertDialog.Builder(medical_advice.this);
                builder2.setTitle("?????? ????????????");
                builder2.setMessage("???????????? ?????????? ?????????????? ???????? ???????? ?????? ?????? ?????? ??????????");

                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder2.setView(editText);
                builder2.setPositiveButton("??????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = dataAccessLayer.SetDiagnos_S_D(the_diagnose,rolev.get("ID"),editText.getText().toString());
                        if (check){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(medical_advice.this);
                            builder1.setTitle("???? ?????????? ??????????");
                            builder1.setMessage("???? ?????????? ?????????? ?????? ?????????????? ???????????? ???????? ???????????? ????????");

                            builder1.create();

                            // create the alert dialog with the
                            // alert dialog builder instance
                            builder1.show();
                        }

                    }
                });
                builder2.create();

                // create the alert dialog with the
                // alert dialog builder instance
                builder2.show();
            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Syndrome_IL(PartofbodyID,PartOfBodyStyleID);
            }
        });


        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}