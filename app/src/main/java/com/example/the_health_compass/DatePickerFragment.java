package com.example.the_health_compass;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Calendar c ;
    EditText Birthday ;

    public DatePickerFragment(EditText editText){
        Birthday = editText;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this::onDateSet,year,month,day);
    }

    public void onDateSet(DatePicker d,int year,int month,int day){
        Birthday.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
    }
}
