package com.cedro.memoriesoftravel.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by emerson on 07/10/16.
 */

public class DialogManager {
    Activity activity;

    public DialogManager(Activity activity){
        this.activity = activity;
    }

    public void showToast(String text){
        Toast.makeText(activity, text,
                Toast.LENGTH_LONG).show();
    }

    public static void showToast(Activity activity,String text){
        Toast.makeText(activity, text,
                Toast.LENGTH_LONG).show();
    }

    public static void showCalendarDialog(Context context, DatePickerDialog.OnDateSetListener listener){
        Calendar calendario = Calendar.getInstance();
        new DatePickerDialog(context, listener, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();

    }
}
