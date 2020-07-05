package com.example.myapplication.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private TextView task;
    private CheckBox is_complete;
    private ImageButton bucket;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        this.task = itemView.findViewById(R.id.task);
        this.is_complete = itemView.findViewById(R.id.is_complete);
        this.bucket = itemView.findViewById(R.id.task_bucket);
    }

    public TextView getTask() {
        return task;
    }

    public void setTask(TextView task) {
        this.task = task;
    }

    public CheckBox getIsComplete() {
        return is_complete;
    }

    public void setIsComplete(CheckBox is_complete) {
        this.is_complete = is_complete;
    }

    public ImageButton getBucket() {
        return bucket;
    }

    public void setBucket(ImageButton bucket) {
        this.bucket = bucket;
    }



}
