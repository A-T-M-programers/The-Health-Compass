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
import android.graphics.RegionIterator;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.RegexValidator;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;

public class Hospital_Management_Page extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    Button[] buttons = new Button[4];
    EditText[] editTexts=new EditText[3];
    ImageView hospital_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_management_page);

        hospital_Image = (ImageView)findViewById(R.id.hospital_Image);

        editTexts[0] = (EditText)findViewById(R.id.ed_hospital_name);
        editTexts[1] = (EditText)findViewById(R.id.ed_hospital_phone);
        editTexts[2] = (EditText)findViewById(R.id.ed_hospital_location);
        // Validations

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.ed_hospital_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ed_hospital_name,"[a-zA-Zأ-ي\\s]+", R.string.invalid_name);


        awesomeValidation.addValidation(this, R.id.ed_hospital_phone, Patterns.PHONE, R.string.invalid_name);


        awesomeValidation.addValidation(this, R.id.ed_hospital_location,RegexTemplate.NOT_EMPTY, R.string.invalid_name);


        buttons[0] = (Button)findViewById(R.id.btn_add_hospital);
        buttons[1] = (Button)findViewById(R.id.btn_update_hospital);
        buttons[2] = (Button)findViewById(R.id.btn_remove_hospital);
        buttons[3] = (Button)findViewById(R.id.btn_save_changes);


        hospital_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePicture();
            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                }
                else{
                    Toast.makeText(getApplicationContext(),"بيانات خاطئة",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Hospital_Management_Page.this);
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
                    hospital_Image.setImageURI(selectedImageUri);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    hospital_Image.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(Hospital_Management_Page.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) ;
            ActivityCompat.requestPermissions(Hospital_Management_Page.this, new String[]{Manifest.permission.CAMERA}, 20);
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