package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.RecyclerViewAdapterNotes;

import java.util.ArrayList;

public class NotesFragment  extends Fragment {

    private ArrayList<NoteItem> cards;
    public RecyclerView recyclerView;
    private EditText searchField;
    public RecyclerViewAdapterNotes recyclerViewAdapterNotes;

    public NotesFragment (ArrayList<NoteItem> cards){
        this.cards = cards;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.note_list_layout,container,false);
        recyclerView = view.findViewById(R.id.recycler_notes_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapterNotes = new RecyclerViewAdapterNotes(cards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapterNotes);
        searchField = view.findViewById(R.id.search_note);

        return view;
    }

}
