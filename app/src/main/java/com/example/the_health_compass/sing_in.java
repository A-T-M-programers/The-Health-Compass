package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class sing_in extends AppCompatActivity {
    private TextView create_account;
    AwesomeValidation awesomeValidation;
    private EditText UserNameOrEmail, Password;
    private Button Sign_In;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    AtomicReference<Sick> s = new AtomicReference<Sick>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.ed_user_name_Sign_In, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ed_password_Sign_In, ".{6,}", R.string.invalid_password);


        UserNameOrEmail = (EditText) findViewById(R.id.ed_user_name_Sign_In);
        Password = (EditText) findViewById(R.id.ed_password_Sign_In);

        create_account = (TextView) findViewById(R.id.tv_create_account);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.ed_user_name_Sign_In, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ed_password_Sign_In, ".{6,}", R.string.invalid_password);


        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountPage();
            }
        });
        Sign_In = (Button) findViewById(R.id.btn_sign_in);
        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    String check = dataAccessLayer.check_sick_sign_in(UserNameOrEmail.getText().toString(), Password.getText().toString(), s);
                    Check(check);
                } else {
                    Toast.makeText(getApplicationContext(), "بيانات خاطئة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openCreateAccountPage() {
        Intent intent = new Intent(this, sign_up.class);
        startActivity(intent);
    }

    public void Check(String datacheck) {
        switch (datacheck) {
            case "Faild Access!":
                AlertDialog.Builder ad = new AlertDialog.Builder(this)
                        .setTitle("Check Internet")
                        .setMessage("Check Your Internet Access!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "OK is clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = ad.create();
                alertDialog.show();
                break;
            case "User":
                AlertDialog.Builder ad1 = new AlertDialog.Builder(this)
                        .setTitle("Access Network")
                        .setMessage("Successfull")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WriteToXml(s.get());
                                OpenHome();
                            }
                        });
                AlertDialog alertDialog1 = ad1.create();
                alertDialog1.show();
                break;
            case "Not Found":
                AlertDialog.Builder ad2 = new AlertDialog.Builder(this)
                        .setTitle("Not Found")
                        .setMessage("Not Found UserName Or Password")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "OK is clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog2 = ad2.create();
                alertDialog2.show();
                break;
        }
    }

    public void OpenHome() {
        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);
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

            e = dom.createElement("Age");
            e.appendChild(dom.createTextNode(s.Age));
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
                /*String filePath = f.getAbsolutePath();*/
                String filePath = this.getFilesDir().getPath().toString() + "/Sick.xml";
                File f = new File(filePath);
                StreamResult streamResult1 = new StreamResult(System.out);
                StreamResult streamResult = null;
                try {
                    streamResult = new StreamResult(new FileWriter(f));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                // send DOM to file
                //tr.transform(new DOMSource(dom),streamResult1);
                tr.transform(new DOMSource(dom), streamResult);

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }/*catch (IOException te){
            System.out.println(te.getMessage());
        }*/ finally {

        }
    }
}