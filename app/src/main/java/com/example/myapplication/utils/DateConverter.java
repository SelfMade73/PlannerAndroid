package com.example.myapplication.utils;

import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    public DateConverter(){}
    final private DateFormat dateFormat = new SimpleDateFormat("dd MMM");
    final private DateFormat dateFormatCurrentDate = new SimpleDateFormat("dd MMM yyyy");

    public Date getDateFromDatePicker(DatePicker picker){
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year  = picker.getYear();
        return new Date(year, month, day);
    }
    public String getDateFromDatePickerAsString(DatePicker picker){
        Date date  = getDateFromDatePicker(picker);
        return dateFormat.format(date) + " " + String.valueOf(picker.getYear());
    }

    public String getCurrentDateAsString(){
        return  dateFormatCurrentDate.format(Calendar.getInstance().getTime());
    }
}
