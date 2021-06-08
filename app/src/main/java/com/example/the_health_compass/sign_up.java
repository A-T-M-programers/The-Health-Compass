package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    HashMap<String, String> DoctorMap = new HashMap<>();
    AwesomeValidation awesomeValidation;
    Doctor D = new Doctor();
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButtons = new RadioButton[4];
    loading_screen loading_screen = new loading_screen(sign_up.this);
    DataAccessLayer dataAccessLayer = new DataAccessLayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn_Create_Account = (Button) findViewById(R.id.btn_create_account);

        // Validations

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_check_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_password, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.ed_config_password, R.id.et_password, R.string.invalid_confirm_password);
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);
       // awesomeValidation.addValidation(this, R.id.ed_birthday, "^(?:(?:31(\\\\/|-|\\\\.)(?:0?[13578]|1[02]))\\\\1|(?:(?:29|30)(\\\\/|-|\\\\.)(?:0?[1,3-9]|1[0-2])\\\\2))(?:(?:1[6-9]|[2-9]\\\\d)?\\\\d{2})$|^(?:29(\\\\/|-|\\\\.)0?2\\\\3(?:(?:(?:1[6-9]|[2-9]\\\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\\\d|2[0-8])(\\\\/|-|\\\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\\\4(?:(?:1[6-9]|[2-9]\\\\d)?\\\\d{2})$", R.string.invalid_birthday);

        // Get Controls To This Class

        // EditTexts

        editTexts[0] = (EditText) findViewById(R.id.et_full_name);
        editTexts[1] = (EditText) findViewById(R.id.et_email);
        editTexts[2] = (EditText) findViewById(R.id.et_check_email);
        editTexts[3] = (EditText) findViewById(R.id.et_password);
        editTexts[4] = (EditText) findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText) findViewById(R.id.ed_birthday);

        // RadioButtons

        radioButtons[0] = (RadioButton) findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb_female);
        radioButtons[2] = (RadioButton) findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb_unable);


        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();

                    DoctorMap.put("D_Full_Name", editTexts[0].getText().toString());
                    DoctorMap.put("Email", editTexts[1].getText().toString());
                    DoctorMap.put("Check_Email", editTexts[2].getText().toString());
                    DoctorMap.put("Password", editTexts[3].getText().toString());
                    DoctorMap.put("Mobile_Phone", editTexts[4].getText().toString());
                    DoctorMap.put("Birthday", editTexts[5].getText().toString());
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
                    D.InputDoctor(DoctorMap);
                    D.InputShareDoctor(DoctorMap, false);
//                    String CheckDataBase = dataAccessLayer.getDoctor(D.D_Full_Name, D.Email, D.Password);
//                    switch (CheckDataBase) {
//                        case "User":
//                            return;
//                        case "Email":
//                            return;
//                        case "Password":
//                            return;
//                        default:
//                            break;
//                    }
//                    boolean CheckSet = dataAccessLayer.SetDoctor(D);
                    // Write To XML File
                    WriteToXML(D);
                    This();
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
    public void WriteToXML(Doctor D) {
        Document document;
        Element element;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            Element rootElement = document.createElement("Doctor");
            element = document.createElement("D_Full_Name");
            element.appendChild(document.createTextNode(D.D_Full_Name));

            element = document.createElement("D_Birthday");
            element.appendChild(document.createTextNode(D.D_Birthday));

            element = document.createElement("D_Email");
            element.appendChild(document.createTextNode(D.Email));

            element = document.createElement("D_Gender");
            element.appendChild(document.createTextNode(D.D_Gender));

            element = document.createElement("D_Check_Email");
            element.appendChild(document.createTextNode(D.Check_Email));

            element = document.createElement("D_ID");
            element.appendChild(document.createTextNode(D.ID));

            element = document.createElement("D_Password");
            element.appendChild(document.createTextNode(D.Password));

            element = document.createElement("D_Mobile_Phone");
            element.appendChild(document.createTextNode(D.Phone_Mobile));

            element = document.createElement("D_Able");
            element.appendChild(document.createTextNode(D.D_Able));

            document.appendChild(rootElement);
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Doctor.dtd");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";
                File file = new File(filePath);
                StreamResult streamResult = new StreamResult(System.out);
                StreamResult streamResult1 = null;
                try {
                    streamResult1 = new StreamResult(new FileWriter(file));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                transformer.transform(new DOMSource(document), streamResult);
            } catch (TransformerException transformerException) {
                System.out.println(transformerException.getMessage());
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }


        } catch (ParserConfigurationException parserConfigurationException) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + parserConfigurationException);
        }
        finally {

        }
    }
}