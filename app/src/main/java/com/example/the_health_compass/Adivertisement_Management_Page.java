package com.example.the_health_compass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Adivertisement_Management_Page extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    Advertising advertising = new Advertising();
    ImageView Adivertisement_Image;
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    Button[] buttons = new Button[4];
    EditText Content;
    TextView[] textViews = new TextView[2];
    Spinner SpinerAddType;
    HashMap<String,String> Adivertisement = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivertisement_management_page);
        Adivertisement_Image = (ImageView)findViewById(R.id.Adivertisement_Image);

        SpinerAddType = (Spinner)findViewById(R.id.sp_add_type);

        textViews[0] = (TextView)findViewById(R.id.textView15);
        textViews[1] = (TextView)findViewById(R.id.tv_ad_type);

        buttons[0] = (Button)findViewById(R.id.btn_add);
        buttons[1] = (Button)findViewById(R.id.btn_update);
        buttons[2] = (Button)findViewById(R.id.btn_remove);
        buttons[3] = (Button)findViewById(R.id.btn_save_changes);

        Content = (EditText)findViewById(R.id.et_Content);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.et_Content, RegexTemplate.NOT_EMPTY,R.string.invalid_Content);
        awesomeValidation.addValidation(this, R.id.et_Content,"[a-zA-Zأ-ي\\s]+", R.string.invalid_Content2);

        Adivertisement_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePicture();
            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Content.setVisibility(View.VISIBLE);
                Adivertisement_Image.setVisibility(View.VISIBLE);
                SpinerAddType.setVisibility(View.VISIBLE);
                textViews[0].setVisibility(View.VISIBLE);
                textViews[1].setVisibility(View.VISIBLE);
                buttons[3].setVisibility(View.VISIBLE);
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Content.setVisibility(View.VISIBLE);
                Adivertisement_Image.setVisibility(View.VISIBLE);
                SpinerAddType.setVisibility(View.VISIBLE);
                textViews[0].setVisibility(View.VISIBLE);
                textViews[1].setVisibility(View.VISIBLE);
                buttons[3].setVisibility(View.VISIBLE);
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"بيانات صحيحة",Toast.LENGTH_SHORT).show();
                    Adivertisement.put("Adivertisement_Content",Content.getText().toString());
                    Adivertisement.put("Adivertisement_Image",Adivertisement_Image.getMatrix().toString());
                    Adivertisement.put("Adivertisement_Type",SpinerAddType.getSelectedItem().toString());
                    advertising.Input_Advertising(Adivertisement);
                    String CheckDataBase = dataAccessLayer.getAdivertisement(advertising.A_Content,advertising.A_Type);
                    //Check if Sink Found in DataBase
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
                    boolean CheckSet = dataAccessLayer.SetAdivertisement(advertising);

                }
                else{
                    Toast.makeText(getApplicationContext(),"بيانات خاطئة",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Adivertisement_Management_Page.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView imageViewADPPCamera = dialogView.findViewById(R.id.imageViewADPPCamera);
        ImageView imageViewADPPGallery = dialogView.findViewById(R.id.imageViewADPPGallery);

        AlertDialog alertDialogProfilePicture = builder.create();
        alertDialogProfilePicture.show();

        imageViewADPPCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissions()) {
                    tackPictureFromCamera();
                }
                alertDialogProfilePicture.cancel();
            }
        });
        imageViewADPPGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tackPictureFromGallery();
                alertDialogProfilePicture.cancel();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void tackPictureFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @SuppressWarnings("deprecation")
    private void tackPictureFromCamera() {
        Intent tackPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tackPicture.resolveActivity(getPackageManager())!= null){
            startActivityForResult(tackPicture,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    Adivertisement_Image.setImageURI(selectedImageUri);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    Adivertisement_Image.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(Adivertisement_Management_Page.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) ;
            ActivityCompat.requestPermissions(Adivertisement_Management_Page.this, new String[]{Manifest.permission.CAMERA}, 20);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            tackPictureFromCamera();
        } else {
            Toast.makeText(getApplicationContext(), "Permission not Granted", Toast.LENGTH_SHORT).show();
        }
    }
}