package com.example.myapplication;

import android.widget.CheckBox;

public class CheckItem {
    private String task;
    private boolean is_complete = false;

    public CheckItem (String task){
        this.task = task;
    }


    public CheckItem (String task,boolean is_complete){
        this.task = task;
        this.is_complete = is_complete;
    }

    String getTask(){
        return this.task;
    }

    boolean getIsComplete(){
        return this.is_complete;
    }


    void setTask(String task){
        this.task = task;
    }

    void setIsComplete(boolean is_complete){
        this.is_complete = is_complete;
    }

}
