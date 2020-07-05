package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.myapplication.activities.CONSTANTS;
import com.example.myapplication.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = CONSTANTS.NOTES_TABLE_NAME)
public class NoteItem implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;

    @TypeConverters(DateConverter.class)
    private Date  date;

    public NoteItem ( int id, String title, String description, Date date ){
        this(title, description, date);
        this.id = id;
    }


    public NoteItem (String title, String description, Date date){
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public NoteItem() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEmpty(){
        return ((title==null) && (description==null));
    }

}
