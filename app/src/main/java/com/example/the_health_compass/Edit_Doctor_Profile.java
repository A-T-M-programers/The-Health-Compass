package com.example.the_health_compass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.santalu.maskedittext.MaskEditText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

public class Edit_Doctor_Profile extends AppCompatActivity {
    Button btn_Save_Changes;
    ArrayList<String> rolev;
    AwesomeValidation awesomeValidation;
    String ID, UserName, UserEmail, CheckEmail, Mobile_Phone, Password, Birthday, Gender, Able, Specialization;
    Doctor D = new Doctor();
    HashMap<String, String> Doctormap = new HashMap<String, String>();
    loading_screen loading_screen = new loading_screen(Edit_Doctor_Profile.this);
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButtons = new RadioButton[4];
    MaskEditText maskEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__doctor);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.et_full_name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.et_mobile_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);
        awesomeValidation.addValidation(this, R.id.et_birthday, RegexTemplate.NOT_EMPTY, R.string.invalid_birthday);
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_check_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_password, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.editTextTextPassword4, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.editTextTextPassword5, R.id.editTextTextPassword4, R.string.invalid_confirm_password);


        editTexts[0] = (EditText) findViewById(R.id.et_full_name);
        maskEditText = (MaskEditText) findViewById(R.id.et_mobile_phone);
        editTexts[1] = (EditText) findViewById(R.id.et_birthday);
        editTexts[2] = (EditText) findViewById(R.id.et_email);
        editTexts[3] = (EditText) findViewById(R.id.et_check_email);
        editTexts[4] = (EditText) findViewById(R.id.editTextTextPassword);
        editTexts[5] = (EditText) findViewById(R.id.editTextTextPassword4);
        radioButtons[0] = (RadioButton) findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb_female);
        radioButtons[2] = (RadioButton) findViewById(R.id.rb_able);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb_unable);

        if (ReadXML_Edit()) {
            editTexts[0].setText(UserName);
            editTexts[1].setText(Birthday);
            editTexts[2].setText(UserEmail);
            editTexts[3].setText(CheckEmail);
            maskEditText.setText(Mobile_Phone);
            if (Gender.equals("ذكر")) {
                radioButtons[0].setChecked(true);
            } else {
                radioButtons[1].setChecked(true);
            }
            if (Able.equals("قادر")) {
                radioButtons[2].setChecked(true);
            } else {
                radioButtons[3].setChecked(true);
            }
        }
        editTexts[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBirthday(editTexts[1]);
            }
        });

        btn_Save_Changes = (Button) findViewById(R.id.btn_save_changes);
        btn_Save_Changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    if (Password.equals(editTexts[4].getText().toString())) {
                        Doctormap.put("D_Full_Name", editTexts[0].getText().toString());
                        Doctormap.put("D_Birthday", editTexts[1].getText().toString());
                        Doctormap.put("D_Email", editTexts[2].getText().toString());
                        Doctormap.put("D_Check_Email", editTexts[3].getText().toString());
                        Doctormap.put("D_Password", editTexts[4].getText().toString());
                        Doctormap.put("D_Mobile_Phone", maskEditText.getText().toString());
                        Doctormap.put("D_Specialization", Specialization);
                        if (radioButtons[0].isChecked()) {
                            Doctormap.put("D_Gender", radioButtons[0].getText().toString());
                        } else if (radioButtons[1].isChecked()) {
                            Doctormap.put("D_Gender", radioButtons[1].getText().toString());
                        }
                        if (radioButtons[2].isChecked()) {
                            Doctormap.put("D_Able", radioButtons[2].getText().toString());
                        } else if (radioButtons[3].isChecked()) {
                            Doctormap.put("D_Able", radioButtons[3].getText().toString());
                        }
                        D.InputDoctor(Doctormap);
                        D.InputShareDoctor(Doctormap, false);
                        WriteToXMLDoctor(D);
                        Open_Profile();
                        loading_screen.startLoadingDialog();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loading_screen.dismissDialog();
                            }
                        }, 5000);
                        Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "بيانات خاطئة ", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public boolean ReadXML_Edit() {
        Document document;
        rolev = new ArrayList<String>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            String FilePath = this.getFilesDir().getPath() + "/Doctor.xml";

            File file = new File(FilePath);
            document = documentBuilder.parse(file);

            Element element = document.getDocumentElement();
            ID = getTextValue(ID, element, "ID");
            rolev.add(ID);
            UserName = getTextValue(UserName, element, "D_Full_Name");
            rolev.add(UserName);
            UserEmail = getTextValue(UserEmail, element, "D_Email");
            rolev.add(UserEmail);
            CheckEmail = getTextValue(CheckEmail, element, "D_Check_Email");
            rolev.add(CheckEmail);
            Mobile_Phone = getTextValue(Mobile_Phone, element, "Mobile_Phone");
            rolev.add(Mobile_Phone);
            Birthday = getTextValue(Birthday, element, "D_Birthday");
            rolev.add(Birthday);
            Password = getTextValue(Password, element, "Password");
            rolev.add(Password);
            Gender = getTextValue(Gender, element, "D_Gender");
            rolev.add(Gender);
            Able = getTextValue(Able, element, "D_Able");
            rolev.add(Able);
            Specialization = getTextValue(Specialization, element, "D_Specialization");
            rolev.add(Specialization);
            return true;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public void WriteToXMLDoctor(Doctor D) {
        Document document;
        Element element;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            Element rootElement = document.createElement("Doctor");

            element = document.createElement("D_Full_Name");
            element.appendChild(document.createTextNode(D.D_Full_Name));
            rootElement.appendChild(element);

            element = document.createElement("D_Birthday");
            element.appendChild(document.createTextNode(D.D_Birthday));
            rootElement.appendChild(element);

            element = document.createElement("D_Email");
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

            element = document.createElement("D_Specialization");
            element.appendChild(document.createTextNode(D.D_Specialization));
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

    public void Open_Profile() {
        Intent intent = new Intent(this, Doctor_Profile.class);
        startActivity(intent);
    }

    public void ShowDialogBirthday(EditText editText) {
        DialogFragment newFragment = new DatePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }
}
