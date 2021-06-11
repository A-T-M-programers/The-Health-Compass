package com.example.the_health_compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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

public class sick_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String UserNameX, UserEmailX, BirthdayX, GenderX, MobilePhoneX, CheckEmailX;
    ArrayList<String> rolev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_profile);

        TextView UserName, UserEmail, Birthday, Gender, MobilePhone, CheckEmail;
        UserName = (TextView) findViewById(R.id.tv_full_name);
        UserEmail = (TextView) findViewById(R.id.tv_Email);
        Birthday = (TextView) findViewById(R.id.tv_birthday);
        Gender = (TextView) findViewById(R.id.tv_gender);
        MobilePhone = (TextView) findViewById(R.id.tv_Phone_Mobile_Sick);
        CheckEmail = (TextView) findViewById(R.id.tv_check_email);
        if (ReadXML_Profile()) {
            UserName.setText(UserNameX);
            UserEmail.setText(UserEmailX);
            Birthday.setText(BirthdayX);
            Gender.setText(GenderX);
            MobilePhone.setText(MobilePhoneX);
            CheckEmail.setText(CheckEmailX);
        }
    }

    public boolean ReadXML_Profile() {
        rolev = new ArrayList<String>();
        Document document;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
            File file = new File(filePath);
            document = documentBuilder.parse(file);
            Element element = document.getDocumentElement();
            UserNameX = getTextValue(UserNameX, element, "S_Full_Name");
            if (UserNameX != null) {
                if (!UserNameX.isEmpty()) {
                    rolev.add(UserNameX);
                }
            }
            UserEmailX = getTextValue(UserEmailX, element, "Email");
            if (UserEmailX != null) {
                if (!UserEmailX.isEmpty()) {
                    rolev.add(UserEmailX);
                }
            }

            BirthdayX = getTextValue(BirthdayX, element, "S_Birthday");
            rolev.add(BirthdayX);
            GenderX = getTextValue(GenderX, element, "S_Gender");
            rolev.add(GenderX);
            MobilePhoneX = getTextValue(MobilePhoneX, element, "Phone_Mobile");
            rolev.add(MobilePhoneX);
            CheckEmailX = getTextValue(CheckEmailX, element, "Check_Email");
            rolev.add(CheckEmailX);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
