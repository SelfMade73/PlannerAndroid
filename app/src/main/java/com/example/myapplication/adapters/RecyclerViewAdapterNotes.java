package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.DateConverter;
import com.example.myapplication.utils.ObservableRxList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecyclerViewAdapterNotes extends RecyclerView.Adapter<NotesViewHolder> {
    private ObservableRxList<NoteItem> cards;

    public RecyclerViewAdapterNotes(ObservableRxList<NoteItem> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item,parent,false);
        return new NotesViewHolder(view);
    }

    public void deleteItem(int index){
        this.cards.remove(index);
        notifyDataSetChanged();
    }

    public void insertItem(int index, NoteItem item){
        NoteItem temp = cards.get(index);
        temp.setTitle(item.getTitle());
        temp.setDate(item.getDate());
        temp.setDescription(item.getDescription());
        notifyDataSetChanged();
    }

    public NoteItem getItem(int index){
        return this.cards.get(index);
    }


    public void setItem(NoteItem other_card) {
        cards.add(other_card);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.getTitle().setText(this.cards.get(position).getTitle());
        holder.getDate().setText(DateConverter.getDateAsString(this.cards.get(position).getDate()));
        holder.getDescription().setText(this.cards.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public List<NoteItem> searchInNotes( CharSequence content) {
        List<NoteItem> result = this.cards.getCurrentList();
        result = result.stream()
                .filter(noteItem -> noteItem.getDescription().contains(content)
                        || noteItem.getTitle().contains(content))
                .collect(Collectors.toList());
        return result;
    }

    public static RecyclerViewAdapterNotes setItems(List<NoteItem> notes) {
        ObservableRxList<NoteItem> temp  = new ObservableRxList<>();
        temp.setList(notes);
        return  new RecyclerViewAdapterNotes(temp);
    }


}
