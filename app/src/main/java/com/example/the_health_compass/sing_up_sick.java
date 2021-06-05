package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class sing_up_sick extends AppCompatActivity {
    HashMap<String, String> sickmap = new HashMap<String, String>();
    Button CreatAccount;
    Sick s = new Sick();
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButton = new RadioButton[2];
    loading_screen loading_screen = new loading_screen(sing_up_sick.this);
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_sick);
        //Get Controler to this class
        editTexts[0] = (EditText) findViewById(R.id.et_full_name);
        editTexts[1] = (EditText) findViewById(R.id.et_email);
        editTexts[2] = (EditText) findViewById(R.id.et_check_email);
        editTexts[3] = (EditText) findViewById(R.id.et_password);
        editTexts[4] = (EditText) findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText) findViewById(R.id.ed_birthday);
        radioButton[0] = (RadioButton) findViewById(R.id.rb_female);
        radioButton[1] = (RadioButton) findViewById(R.id.rb_male);
        CreatAccount = (Button) findViewById(R.id.btn_create_account);
        CreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add The Data to HashMap
                sickmap.put("S_Full_Name", editTexts[0].getText().toString());
                sickmap.put("Email", editTexts[1].getText().toString());
                sickmap.put("Check_Email", editTexts[2].getText().toString());
                sickmap.put("Password", editTexts[3].getText().toString());
                sickmap.put("Phone_Mobile", editTexts[4].getText().toString());
                sickmap.put("S_Birthday", editTexts[5].getText().toString());
                if (radioButton[0].isChecked()) {
                    sickmap.put("S_Gender", radioButton[0].getText().toString());
                } else {
                    sickmap.put("S_Gender", radioButton[1].getText().toString());
                }
                //Add Information Sick to class sick
                s.InPutSick(sickmap);
                s.InputShare(sickmap, false);
                String CheckDataBase = dataAccessLayer.getsick(s.S_Full_Name,s.Email,s.Password);
                //Check if Sink Found in DataBase
                switch (CheckDataBase){
                    case "User":return;
                    case "Email":return;
                    case "Password":return;
                    default:break;
                }
                //Add sick to database
                boolean CheckSet = dataAccessLayer.SetSick(s);
                //Add sick to File Xml
                WriteToXml();
                This();
                loading_screen.startLoadingDialog();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    loading_screen.dismissDialog();
                    }
                },5000);
            }
        });
    }
    //Move to home page
    public void This() {
        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);
    }
    //Write data sick on file xml
    public void WriteToXml() {
        Document dom;
        Element e;
        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();
            // create the root element
            Element rootEle = dom.createElement("Sick");
            // create data elements and place them under root
            e = dom.createElement("S_Full_Name");
            e.appendChild(dom.createTextNode(s.S_Full_Name));
            rootEle.appendChild(e);

            e = dom.createElement("S_Birthday");
            e.appendChild(dom.createTextNode(s.S_Birthday));
            rootEle.appendChild(e);

            e = dom.createElement("S_Gender");
            e.appendChild(dom.createTextNode(s.S_Gender));
            rootEle.appendChild(e);

            e = dom.createElement("Email");
            e.appendChild(dom.createTextNode(s.Email));
            rootEle.appendChild(e);

            e = dom.createElement("Check_Email");
            e.appendChild(dom.createTextNode(s.Check_Email));
            rootEle.appendChild(e);

            e = dom.createElement("ID");
            e.appendChild(dom.createTextNode(s.ID));
            rootEle.appendChild(e);

            e = dom.createElement("Password");
            e.appendChild(dom.createTextNode(s.Password));
            rootEle.appendChild(e);


            e = dom.createElement("Phone_Mobile");
            e.appendChild(dom.createTextNode(s.Phone_Mobile));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Sick.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
                File f = new File(filePath);
                StreamResult streamResult = new StreamResult(new FileWriter(f));
                StreamResult streamResult1 = new StreamResult(System.out);
                // send DOM to file
                //tr.transform(new DOMSource(dom),streamResult1);
                tr.transform(new DOMSource(dom), streamResult);

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        } finally {

        }
    }
}

