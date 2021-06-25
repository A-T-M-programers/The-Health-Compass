package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class Edit_Profile_Sick extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    ArrayList<String> rolev;
    String ID, UserName, UserEmail, CheckEmail, Mobile_Phone,Password, Birthday, Gender;
    EditText[] editTexts = new EditText[6];
    RadioButton[] radioButtons = new RadioButton[2];
    loading_screen loading_screen = new loading_screen(Edit_Profile_Sick.this);
    Button btn_Save_Change;
    Sick s = new Sick();
    HashMap<String,String> sickmap = new HashMap<String, String>();
    MaskEditText maskEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__sick);

        // Validations

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.et_full_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.et_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.et_check_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword2, ".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword3,R.id.editTextTextPassword2 ,R.string.invalid_confirm_password);
        awesomeValidation.addValidation(this,R.id.et_mobile_phone, Patterns.PHONE,R.string.invalid_mobile_phone_number);
        awesomeValidation.addValidation(this,R.id.et_birthday,RegexTemplate.NOT_EMPTY,R.string.invalid_birthday);


        editTexts[0] = (EditText)findViewById(R.id.et_full_name);
        editTexts[1] = (EditText)findViewById(R.id.et_email);
        editTexts[2] = (EditText)findViewById(R.id.et_check_email);
        editTexts[3] = (EditText)findViewById(R.id.editTextTextPassword);
        editTexts[4] = (EditText)findViewById(R.id.editTextTextPassword2);
        maskEditText = (MaskEditText)findViewById(R.id.et_mobile_phone);
        editTexts[5] = (EditText)findViewById(R.id.et_birthday);
        radioButtons[0] = (RadioButton)findViewById(R.id.rb_male);
        radioButtons[1] = (RadioButton)findViewById(R.id.rb_female);
        if(ReadXML_Edit()) {
            editTexts[0].setText(UserName);
            editTexts[1].setText(UserEmail);
            editTexts[2].setText(CheckEmail);
            maskEditText.setText(Mobile_Phone);
            editTexts[5].setText(Birthday);
            if(Gender.equals("ذكر")) {
                radioButtons[0].setChecked(true);
            }else {
                radioButtons[1].setChecked(true);
            }
        }

        editTexts[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBirthday(editTexts[5]);
            }
        });
        btn_Save_Change = (Button)findViewById(R.id.btn_save_changes);
        btn_Save_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                    Toast.makeText(getApplicationContext(), "بيانات صحيحة", Toast.LENGTH_SHORT).show();
                    if (Password.equals(editTexts[3].getText().toString())) {
                        sickmap.put("S_Full_Name", editTexts[0].getText().toString());
                        sickmap.put("Email", editTexts[1].getText().toString());
                        sickmap.put("Check_Email", editTexts[2].getText().toString());
                        sickmap.put("Password", editTexts[4].getText().toString());
                        sickmap.put("Phone_Mobile", maskEditText.getText().toString());
                        sickmap.put("S_Birthday", editTexts[5].getText().toString());
                        if (radioButtons[0].isChecked()) {
                            sickmap.put("S_Gender", radioButtons[0].getText().toString());
                        } else {
                            sickmap.put("S_Gender", radioButtons[1].getText().toString());
                        }
                        s.InPutSick(sickmap);
                        s.InputShare(sickmap,false);
                        WriteToXml(s);
                        Open_Profile();
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
            }
        });
    }
    public void Open_Profile(){
        Intent intent= new Intent(this,sick_profile.class);
        startActivity(intent);
    }

    // Read XML File To Fill The Controls Data
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
            String FilePath = this.getFilesDir().getPath() + "/Sick.xml";

            File file = new File(FilePath);
            document = documentBuilder.parse(file);

            Element element = document.getDocumentElement();
            ID = getTextValue(ID, element, "ID");
            rolev.add(ID);
            UserName = getTextValue(UserName, element, "S_Full_Name");
            rolev.add(UserName);
            UserEmail = getTextValue(UserEmail, element, "Email");
            rolev.add(UserEmail);
            CheckEmail = getTextValue(CheckEmail, element, "Check_Email");
            rolev.add(CheckEmail);
            Mobile_Phone = getTextValue(Mobile_Phone, element, "Phone_Mobile");
            rolev.add(Mobile_Phone);
            Birthday = getTextValue(Birthday, element, "S_Birthday");
            rolev.add(Birthday);
            Password = getTextValue(Password,element,"Password");
            rolev.add(Password);
            Gender = getTextValue(Gender, element, "S_Gender");
            rolev.add(Gender);
            return true;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
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
    public void ShowDialogBirthday(EditText editText) {
        DialogFragment newFragment = new DatePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }
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
}