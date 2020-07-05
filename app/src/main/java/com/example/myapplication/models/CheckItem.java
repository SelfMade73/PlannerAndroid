package com.example.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myapplication.activities.CONSTANTS;
import com.example.myapplication.utils.CheckConverter;

import java.io.Serializable;

@Entity(tableName = CONSTANTS.TASKS_TABLE_NAME)
public class CheckItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task;

    @TypeConverters(CheckConverter.class)
    private boolean complete = false;

    public CheckItem (String task){
        this.task = task;
    }

    public CheckItem() {}

    public String getTask(){
        return this.task;
    }

    public boolean getComplete(){
        return this.complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task){
        this.task = task;
    }

    public void setComplete(boolean is_complete){
        this.complete = is_complete;
    }

}
