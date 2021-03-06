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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Hospital_Management_Page extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    Button[] buttons = new Button[5];
    EditText[] editTexts=new EditText[5];
    ImageView hospital_Image;
    TextView Photo;
    GridView datagridView;
    ArrayList<ListHospital>Hospitals = new ArrayList<>();
    HashMap<String,String> HospitalMap= new HashMap<String, String>();
    Hospital H = new Hospital();
    DataAccessLayer dataAccessLayer = new DataAccessLayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_management_page);

        datagridView = (GridView)findViewById(R.id.data);
        hospital_Image = (ImageView)findViewById(R.id.hospital_Image);

        Photo = (TextView)findViewById(R.id.tv_photo);

        editTexts[0] = (EditText)findViewById(R.id.ed_hospital_name);
        editTexts[1] = (EditText)findViewById(R.id.ed_hospital_phone);
        editTexts[2] = (EditText)findViewById(R.id.ed_hospital_location);
        editTexts[3] = (EditText)findViewById(R.id.ed_hospital_description);
        editTexts[4] = (EditText)findViewById(R.id.ed_ID);
        // Validations

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.ed_hospital_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ed_hospital_name,"[a-zA-Z??-??\\s]+", R.string.invalid_name);


        awesomeValidation.addValidation(this, R.id.ed_hospital_phone, Patterns.PHONE, R.string.invalid_mobile_phone_number);


        awesomeValidation.addValidation(this, R.id.ed_hospital_location,RegexTemplate.NOT_EMPTY, R.string.invalid_Content);

        awesomeValidation.addValidation(this,R.id.ed_hospital_description,RegexTemplate.NOT_EMPTY,R.string.invalid_Content);


        buttons[0] = (Button)findViewById(R.id.btn_add_hospital);
        buttons[1] = (Button)findViewById(R.id.btn_update_hospital);
        buttons[2] = (Button)findViewById(R.id.btn_remove_hospital);
        buttons[3] = (Button)findViewById(R.id.btn_save_changes);
        buttons[4] = (Button)findViewById(R.id.btn_remove_hospital_by_id);

        String Check = dataAccessLayer.getHospitals(Hospitals);

        hospital_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePicture();
            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Photo.setVisibility(View.VISIBLE);
                editTexts[0].setVisibility(View.VISIBLE);
                editTexts[1].setVisibility(View.VISIBLE);
                editTexts[2].setVisibility(View.VISIBLE);
                editTexts[3].setVisibility(View.VISIBLE);

                buttons[3].setVisibility(View.VISIBLE);

                hospital_Image.setVisibility(View.VISIBLE);

            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo.setVisibility(View.VISIBLE);
                editTexts[0].setVisibility(View.VISIBLE);
                editTexts[1].setVisibility(View.VISIBLE);
                editTexts[2].setVisibility(View.VISIBLE);
                editTexts[3].setVisibility(View.VISIBLE);
                buttons[3].setVisibility(View.VISIBLE);

                hospital_Image.setVisibility(View.VISIBLE);
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
                    Toast.makeText(getApplicationContext(),"???????????? ??????????",Toast.LENGTH_SHORT).show();
                    HospitalMap.put("H_Name",editTexts[0].getText().toString());
                    HospitalMap.put("H_Phone",editTexts[1].getText().toString());
                    HospitalMap.put("H_Location",editTexts[2].getText().toString());
                    HospitalMap.put("H_Description",editTexts[3].getText().toString());
                    H.InputHospital(HospitalMap);
                    String CheckDataBase = dataAccessLayer.getHospital(H.H_Name, H.H_Phone);
                    switch (CheckDataBase) {
                        case "Name":
                            return;
                        case "Phone":
                            return;
                        default:
                            break;
                    }
                    boolean CheckSet = dataAccessLayer.SetHospital(H);
                }
                else{
                    Toast.makeText(getApplicationContext(),"???????????? ??????????",Toast.LENGTH_SHORT).show();
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