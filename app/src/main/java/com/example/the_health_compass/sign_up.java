package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class sign_up extends AppCompatActivity {
    Button btn_Create_Account;
    HashMap<String, String> DoctorMap = new HashMap<String, String>();
    AwesomeValidation awesomeValidation;
    Doctor D = new Doctor();
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButtons = new RadioButton[4];
    Spinner subscription;
    loading_screen loading_screen = new loading_screen(sign_up.this);
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    String[] subscriptions = new String[]{"عظمية", "قلبية", "أطفال"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn_Create_Account = (Button) findViewById(R.id.btn_create_account);

        // Validations

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Validate Doctor Name
        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_full_name, "[a-zA-Zأ-ي\\s]+", R.string.invalid_name);

        // Validate Doctor Email
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        // Validate Doctor Check Email
        awesomeValidation.addValidation(this, R.id.et_check_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        // Validate Doctor Password
        awesomeValidation.addValidation(this, R.id.et_password, "[1-9a-zA-Z\\s]+", R.string.invalid_password2);
        awesomeValidation.addValidation(this, R.id.et_password, ".{6,}", R.string.invalid_password);

        // Validate Doctor Confirm Password
        awesomeValidation.addValidation(this, R.id.ed_config_password, R.id.et_password, R.string.invalid_confirm_password);

        // Validate Doctor Mobile Phone Number
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);

        // Validate Doctor Birthday
        awesomeValidation.addValidation(this, R.id.ed_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);

        // Get Controls To This Class

        // EditTexts

        // Doctor Full Name
        editTexts[0] = (EditText) findViewById(R.id.et_full_name);

        // Doctor Email
        editTexts[1] = (EditText) findViewById(R.id.et_email);

        // Doctor CheckEmail
        editTexts[2] = (EditText) findViewById(R.id.et_check_email);

        // Doctor Password
        editTexts[3] = (EditText) findViewById(R.id.et_password);

        // Doctor Mobile Phone Number
        editTexts[4] = (EditText) findViewById(R.id.et_mobile_phone);

        // Doctor Birthday
        editTexts[5] = (EditText) findViewById(R.id.ed_birthday);

        // Doctor Specialization
        subscription = (Spinner) findViewById(R.id.sp_subscription);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subscriptions);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subscription.setAdapter(stringArrayAdapter);

        // Event On Click For Doctor Birthday To Enter The Birthday Using Interface
        editTexts[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBirthday(editTexts[5]);
            }
        });

        // RadioButtons

        // Doctor Gender
        radioButtons[0] = (RadioButton) findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb_female);

        // Doctor Ability To Respond At Home
        radioButtons[2] = (RadioButton) findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb_unable);

        // Button On Click Event To Save Data
        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();

                    // Add Data To HashMap

                    DoctorMap.put("D_Full_Name", editTexts[0].getText().toString());
                    DoctorMap.put("D_Email", editTexts[1].getText().toString());
                    DoctorMap.put("D_Check_Email", editTexts[2].getText().toString());
                    DoctorMap.put("D_Password", editTexts[3].getText().toString());
                    DoctorMap.put("D_Mobile_Phone", editTexts[4].getText().toString());
                    DoctorMap.put("D_Birthday", editTexts[5].getText().toString());
                    DoctorMap.put("D_Specialization", subscription.getSelectedItem().toString());

                    if (radioButtons[0].isChecked()) {
                        DoctorMap.put("D_Gender", radioButtons[0].getText().toString());
                    } else if (radioButtons[1].isChecked()) {
                        DoctorMap.put("D_Gender", radioButtons[1].getText().toString());
                    }
                    if (radioButtons[2].isChecked()) {
                        DoctorMap.put("D_Able", radioButtons[2].getText().toString());
                    } else if (radioButtons[3].isChecked()) {
                        DoctorMap.put("D_Able", radioButtons[3].getText().toString());
                    }
                    // Add Data From HashMap To Object From Type Doctor
                    D.InputDoctor(DoctorMap);

                    D.InputShareDoctor(DoctorMap, false);

                    // Check If Doctor Is In Database
                    String CheckDataBase = dataAccessLayer.getDoctor(D.D_Full_Name, D.Email, D.Password);
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

                    // Add Doctor Data To Database
                    boolean CheckSet = dataAccessLayer.SetDoctor(D);

                    // Write To XML File
                    WriteToXMLDoctor(D);

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

    public void WriteToXMLDoctor(Doctor D) {
        Document document;
        Element element;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {

            // use factory to get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // create instance of DOM
            document = documentBuilder.newDocument();

            // Create A Root Element
            Element rootElement = document.createElement("Doctor");

            // Create A Elements Under The Root Element And Data To Them

            element = document.createElement("D_Full_Name");
            element.appendChild(document.createTextNode(D.D_Full_Name));
            rootElement.appendChild(element);

            element = document.createElement("D_Birthday");
            element.appendChild(document.createTextNode(D.D_Birthday));
            rootElement.appendChild(element);

            element = document.createElement("Email");
            element.appendChild(document.createTextNode(D.Email));
            rootElement.appendChild(element);

            element = document.createElement("D_Gender");
            element.appendChild(document.createTextNode(D.D_Gender));
            rootElement.appendChild(element);

            element = document.createElement("D_Check_Email");
            element.appendChild(document.createTextNode(D.Check_Email));
            rootElement.appendChild(element);

            element = document.createElement("ID");
            element.appendChild(document.createTextNode(D.ID));
            rootElement.appendChild(element);

            element = document.createElement("Password");
            element.appendChild(document.createTextNode(D.Password));
            rootElement.appendChild(element);

            element = document.createElement("Mobile_Phone");
            element.appendChild(document.createTextNode(D.Phone_Mobile));
            rootElement.appendChild(element);

            element = document.createElement("D_Able");
            element.appendChild(document.createTextNode(D.D_Able));
            rootElement.appendChild(element);

            document.appendChild(rootElement);
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Doctor.dtd");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                String filePath = this.getFilesDir().getPath() + "/Doctor.xml";
                System.out.println(filePath);
                File file = new File(filePath);
                StreamResult streamResult = new StreamResult(System.out);
                StreamResult streamResult1 = null;
                try {
                    streamResult1 = new StreamResult(new FileWriter(file));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                // send DOM to file
                //tr.transform(new DOMSource(dom),streamResult1);
                transformer.transform(new DOMSource(document), streamResult1);
            } catch (TransformerException transformerException) {
                System.out.println(transformerException.getMessage());
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }

        } catch (ParserConfigurationException parserConfigurationException) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + parserConfigurationException);
        } finally {
        }
    }

    // Method To Show Interface To Enter Birthday
    public void ShowDialogBirthday(EditText editText) {
        DialogFragment newFragment = new DatePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }
}