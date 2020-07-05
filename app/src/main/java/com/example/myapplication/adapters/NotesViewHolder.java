package com.example.myapplication.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView date;
    TextView description;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.note_title);
        this.date = itemView.findViewById(R.id.note_date);
        this.description = itemView.findViewById(R.id.note_description);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getDescription() {
        return description;
    }

}
