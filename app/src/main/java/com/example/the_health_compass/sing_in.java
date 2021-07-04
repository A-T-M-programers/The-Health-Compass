package com.example.the_health_compass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class sing_in extends AppCompatActivity {
    private TextView create_account,forgetpassowrd;
    AwesomeValidation awesomeValidation;
    private EditText UserNameOrEmail, Password;
    private Button Sign_In;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    AtomicReference<Sick> s = new AtomicReference<Sick>();
    AtomicReference<Doctor> d = new AtomicReference<Doctor>();
    String check;
    EditText email =null;
    EditText checkemail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validate User Name
        awesomeValidation.addValidation(this, R.id.ed_user_name_Sign_In, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ed_user_name_Sign_In, "[a-zA-Zأ-ي\\s]+", R.string.invalid_name);

        // Validate Password
        awesomeValidation.addValidation(this, R.id.ed_password_Sign_In, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.ed_password_Sign_In,"[a-zA-Z0-9\\s]+",R.string.invalid_password2);


        //set control UserName or Email to variable UserNameOrEmail
        UserNameOrEmail = (EditText) findViewById(R.id.ed_user_name_Sign_In);
        forgetpassowrd = (TextView) findViewById(R.id.tv_forget_password);
        forgetpassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPassword();
            }
        });
        //set control password to variable Password
        Password = (EditText) findViewById(R.id.ed_password_Sign_In);
        //set control create account to variable create account
        create_account = (TextView) findViewById(R.id.tv_create_account);
        //Event Create Acount
        create_account.setOnClickListener(new View.OnClickListener() {
            //Event Click
            @Override
            public void onClick(View v) {
                //move user to page create account
                openCreateAccountPage();
            }
        });
        //set control Sign in to variable Sign in
        Sign_In = (Button) findViewById(R.id.btn_sign_in);
        //Event Sign in
        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    String user = UserNameOrEmail.getText().toString();

                    // Check If User Is Doctor
                    if (user.substring(0,2).toLowerCase().equals("dr") |UserNameOrEmail.getText().toString().contains("doctor.com")){
                        String check = dataAccessLayer.check_doctor_sign_in(UserNameOrEmail.getText().toString(), Password.getText().toString(), d);
                        Check(check);
                    }
                    else {
                        String check = dataAccessLayer.check_sick_sign_in(UserNameOrEmail.getText().toString(), Password.getText().toString(), s);
                        Check(check);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "بيانات خاطئة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Method To Open The Create Account Page
    public void openCreateAccountPage() {
        // Select Account Type
        AlertDialog.Builder builder = new AlertDialog.Builder(sing_in.this);
        builder.setTitle("ما نوع الحساب التي تريد انشائه");
        builder.setMessage("يجب عليك تحديد نوع الحساب المراد انشائه");
        builder.setCancelable(false);
        builder.setPositiveButton("مريض", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(sing_in.this, sing_up_sick.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("طبيب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(sing_in.this, sing_up_sick.class);
                startActivity(intent);
            }
        });
        builder.create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    // Che
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
            case "Sick":
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
            case "Doctor":
                AlertDialog.Builder ad3 = new AlertDialog.Builder(this)
                        .setTitle("Access Network")
                        .setMessage("Successfull")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WriteToXml(d.get());
                                OpenHome();
                            }
                        });
                AlertDialog alertDialog3 = ad3.create();
                alertDialog3.show();
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

    // Method To Open Home Page
    public void OpenHome() {
        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);
    }
    // Method To Add Sick Data To XML File
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

    // Method To Add Doctor Data To XML File
    public void WriteToXml(Doctor d) {

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
            Element rootEle = dom.createElement("Doctor");
            // create data elements and place them under root
            e = dom.createElement("Full_Name");
            e.appendChild(dom.createTextNode(d.D_Full_Name));
            rootEle.appendChild(e);

            e = dom.createElement("Birthday");
            e.appendChild(dom.createTextNode(d.D_Birthday));
            rootEle.appendChild(e);

            e = dom.createElement("Age");
            e.appendChild(dom.createTextNode(d.Age));
            rootEle.appendChild(e);

            e = dom.createElement("Gender");
            e.appendChild(dom.createTextNode(d.D_Gender));
            rootEle.appendChild(e);

            e = dom.createElement("Email");
            e.appendChild(dom.createTextNode(d.Email));
            rootEle.appendChild(e);

            e = dom.createElement("Check_Email");
            e.appendChild(dom.createTextNode(d.Check_Email));
            rootEle.appendChild(e);

            e = dom.createElement("ID");
            e.appendChild(dom.createTextNode(d.ID));
            rootEle.appendChild(e);

            e = dom.createElement("Password");
            e.appendChild(dom.createTextNode(d.Password));
            rootEle.appendChild(e);


            e = dom.createElement("Phone_Mobile");
            e.appendChild(dom.createTextNode(d.Phone_Mobile));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Doctor.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                /*String filePath = f.getAbsolutePath();*/
                String filePath = this.getFilesDir().getPath().toString() + "/Doctor.xml";
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
    public void ForgetPassword(){
        String sEmail = "tofikdaowd@gmail.com",sPassword = "1Qa2Ws3Ed4Rf5Tg";
        email = new EditText(this);
        checkemail = new EditText(this);
        check = random();
        AlertDialog.Builder builder = new AlertDialog.Builder(sing_in.this);
        builder.setTitle("نسيان كلمة المرور");
        builder.setMessage("الرجاء أدخال البريد الألكتروني");
        email.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(email);
        builder.setCancelable(false);
        builder.setPositiveButton("مريض", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.starttls.enable","true");
                    properties.put("mail.smtp.host","smtp.gmail.com");
                    properties.put("mail.smtp.port","587");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sEmail,sPassword);
                        }
                    });

                    try {

                        Message message = new MimeMessage(session);

                        message.setFrom(new InternetAddress(sEmail));

                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getText().toString().trim()));

                        message.setSubject("التحقق من البريد الالكتروني");

                        message.setText(check);

                        new SendMail().execute(message);


                    }catch (MessagingException e){e.printStackTrace();}

                }catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("طبيب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth","true");
                    properties.put("mail.smtp.starttls.enable","true");
                    properties.put("mail.smtp.host","smtp.gmail.com");
                    properties.put("mail.smtp.port","587");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sEmail,sPassword);
                        }
                    });

                    try {

                        Message message = new MimeMessage(session);

                        message.setFrom(new InternetAddress(sEmail));

                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getText().toString().trim()));

                        message.setSubject("التحقق من البريد الالكتروني");

                        message.setText(check);

                        new SendMail().execute(message);


                    }catch (MessagingException e){e.printStackTrace();}

                }catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });
        builder.create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private String random(){
        byte[] array = new byte[5]; // length is bounded by 5
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-16"));
        return generatedString;
    }

    private class SendMail extends AsyncTask<Message,String,String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(sing_in.this,"Please Wait","Sending Code .......",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String se) {
            super.onPostExecute(se);
            progressDialog.dismiss();
            if (se.equals("Success")){

                AlertDialog.Builder builder1 = new AlertDialog.Builder(sing_in.this);
                builder1.setTitle("نسيان كلمة المرور");
                builder1.setMessage("ادخل الرمز المرسل بالبريد ");
                checkemail.setInputType(InputType.TYPE_CLASS_TEXT);
                builder1.setView(email);
                builder1.setCancelable(false);
                builder1.setPositiveButton("مريض", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (check.equals(checkemail.getText().toString())){
                            String checkemail = dataAccessLayer.check_sick_sign_inbyemail(email.getText().toString(),s);
                            Check(checkemail);
                        }
                        else if (check!=checkemail.getText().toString()){
                            Toast.makeText(sing_in.this,"الرمز غير مطابق",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder1.setNegativeButton("طبيب", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (check.equals(checkemail.getText().toString())){
                            String checkemail = dataAccessLayer.check_doctor_sign_inbyemail(email.getText().toString(),d);
                            Check(checkemail);
                        }
                        else if (check!=checkemail.getText().toString()){
                            Toast.makeText(sing_in.this,"الرمز غير مطابق",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder1.create();
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(sing_in.this);
//                builder.setCancelable(false);
//                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
//                builder.setMessage("Mail Send Successfully");
            }
        }
    }
}