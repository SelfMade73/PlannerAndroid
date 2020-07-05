package com.example.myapplication.utils;

import androidx.room.TypeConverter;

public class CheckConverter {

    @TypeConverter
    public int getCheckedValueFromBoolean( boolean isChecked){
        return (isChecked)?1:0;
    }

}
