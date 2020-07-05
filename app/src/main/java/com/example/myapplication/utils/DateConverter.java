package com.example.myapplication.utils;

import android.util.Log;
import android.widget.DatePicker;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    public DateConverter(){}
    final private static DateFormat dateFormat = new SimpleDateFormat("dd MMM ");
    final private static DateFormat dateFormatCurrentDate = new SimpleDateFormat("dd MMM yyyy");

    public Date getDateFromDatePicker(DatePicker picker){
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year  = picker.getYear();
        return new Date(year, month, day);
    }
    public String getDateFromDatePickerAsString(DatePicker picker){
        Date date  = getDateFromDatePicker(picker);
        return dateFormat.format(date) + " " + picker.getYear();
    }

    public String getCurrentDateAsString(){
        return  dateFormatCurrentDate.format(Calendar.getInstance().getTime());
    }


    public static String getDateAsString(Date date){
        Calendar.getInstance().setTimeInMillis(date.getTime());
        return dateFormatCurrentDate.format(Calendar.getInstance().getTime());
    }

    @TypeConverter
    public long getDateAsLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date setDateFromInteger(long time ) {
        return new Date(time);
    }

}
