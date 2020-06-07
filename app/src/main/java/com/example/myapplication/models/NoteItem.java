package com.example.myapplication.models;

import java.io.Serializable;
import java.util.Date;

public class NoteItem implements Serializable {
    private String title;
    private String description;
    private Date  date;

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

    public long getDateAsLong(){
        return this.date.getTime();
    }

    public void setDateFromInteger(long time ) {
        this.date = new Date(time);
    }
}
