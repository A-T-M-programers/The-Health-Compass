package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.santalu.maskedittext.MaskEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class sing_up_sick extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    HashMap<String, String> sickmap = new HashMap<String, String>();
    Button CreatAccount;
    Sick s = new Sick();
    EditText[] editTexts = new EditText[5];
    RadioButton[] radioButton = new RadioButton[2];
    loading_screen loading_screen = new loading_screen(sing_up_sick.this);
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    String lastChar = "";
    MaskEditText maskedEditText;
    int counts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_sick);
        CreatAccount = (Button) findViewById(R.id.btn_create_account);

        // Validations
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validate Sick Name
        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_full_name,"[a-zA-Zأ-ي\\s]+", R.string.invalid_name);

        // Validate Sick Email
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        // Validate Sick Check Email
        awesomeValidation.addValidation(this, R.id.et_check_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        // Validate Sick Password
        awesomeValidation.addValidation(this,R.id.et_password,"[1-9a-zA-Z\\s]+",R.string.invalid_password2);
        awesomeValidation.addValidation(this, R.id.et_password, ".{6,}", R.string.invalid_password);

        // Validate Sick Confirm Password
        awesomeValidation.addValidation(this, R.id.ed_config_password, R.id.et_password, R.string.invalid_confirm_password);

        // Validate Sick Mobile Phone Number
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);

        // Validate Sick Birthday
        awesomeValidation.addValidation(this, R.id.ed_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);


        //Get Controler to this class

        // Sick Name
        editTexts[0] = (EditText) findViewById(R.id.et_full_name);

        // Sick Email
        editTexts[1] = (EditText) findViewById(R.id.et_email);

        // Sick Check Email
        editTexts[2] = (EditText) findViewById(R.id.et_check_email);

        // Sick Password
        editTexts[3] = (EditText) findViewById(R.id.et_password);

        // Sick Mobile Phone Number
        maskedEditText = (MaskEditText) findViewById(R.id.et_mobile_phone);

        // Sick Birthday
        editTexts[4] = (EditText) findViewById(R.id.ed_birthday);

        // Event On Click For Sick Birthday
        editTexts[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBirthday(editTexts[4]);
            }
        });
        // RadioButtons

        // Sick Gender
        radioButton[0] = (RadioButton) findViewById(R.id.rb_female);
        radioButton[1] = (RadioButton) findViewById(R.id.rb_male);

        CreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();

                    // Add The Data to HashMap
                    sickmap.put("S_Full_Name", editTexts[0].getText().toString());
                    sickmap.put("Email", editTexts[1].getText().toString());
                    sickmap.put("Check_Email", editTexts[2].getText().toString());
                    sickmap.put("Password", editTexts[3].getText().toString());
                    sickmap.put("Phone_Mobile", maskedEditText.getText().toString());
                    sickmap.put("S_Birthday", editTexts[4].getText().toString());

                    if (radioButton[0].isChecked()) {
                        sickmap.put("S_Gender", radioButton[0].getText().toString());
                    } else {
                        sickmap.put("S_Gender", radioButton[1].getText().toString());
                    }

                    //Add Information Sick to class sick
                    s.InPutSick(sickmap);
                    s.InputShare(sickmap, false);

                    //Check if Sink Found in DataBase
                    String CheckDataBase = dataAccessLayer.getsick(s.S_Full_Name, s.Email, s.Password);
                    switch (CheckDataBase) {
                        case "User":
                            return;
                        case "Email":
                            return;
                        case "Password":
                            return;
                        default:
                            break;
                    }

                    // Add sick to database
                    boolean CheckSet = dataAccessLayer.SetSick(s);

                    // Add sick to File Xml
                    WriteToXml(s);

                    // Method To Move To Home Page
                    This();

                    // To Add Animation After Click On Sing Up Button
                    loading_screen.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading_screen.dismissDialog();
                        }
                    }, 5000);


                } else {
                    Toast.makeText(getApplicationContext(), "بيانات خاطئة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Move to home page
    public void This() {
        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);
    }

    //Write data sick on file xml
    public void WriteToXml(Sick s) {

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
            e = dom.createElement("Full_Name");
            e.appendChild(dom.createTextNode(s.S_Full_Name));
            rootEle.appendChild(e);

            e = dom.createElement("Birthday");
            e.appendChild(dom.createTextNode(s.S_Birthday));
            rootEle.appendChild(e);

            e = dom.createElement("Age");
            e.appendChild(dom.createTextNode(s.Age));
            rootEle.appendChild(e);

            e = dom.createElement("Gender");
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
                String filePath = this.getFilesDir().getPath() + "/Sick.xml";
                File f = new File(filePath);
                StreamResult streamResult1 = new StreamResult(System.out);
                StreamResult streamResult = null;
                try {
                    streamResult = new StreamResult(new FileWriter(f));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

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
    // Method To Show Interface To Enter Birthday
    public void ShowDialogBirthday(EditText editText) {
        DialogFragment newFragment = new DatePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }

}

