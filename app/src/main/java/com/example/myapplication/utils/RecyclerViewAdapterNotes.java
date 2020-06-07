package com.example.myapplication.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.NoteItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapterNotes extends RecyclerView.Adapter<RecyclerViewAdapterNotes.MyViewHolderNotes> {
    private ArrayList<NoteItem> cards;

    public RecyclerViewAdapterNotes(ArrayList<NoteItem> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterNotes.MyViewHolderNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item,parent,false);
        return new RecyclerViewAdapterNotes.MyViewHolderNotes(view);
    }
/*
    public void setItems(Collection<CheckItem> other_cards) {
        cards.addAll(other_cards);
        notifyDataSetChanged();
    }
    public void toggleItem (int position) {
        cards.get(position).setIsComplete(!cards.get(position).getIsComplete());
        notifyItemChanged(position);
    }
    //start with 1 - complete task , 0 - not complete yet
    public ArrayList<String> getTodoListAsStringList() {
        ArrayList <String> result = new ArrayList<>();
        for (CheckItem card : cards){
            if (card.getIsComplete()){
                result.add("1".concat(card.getTask()));
            }else {
                result.add("0".concat(card.getTask()));
            }

        }
        return  result;
    }

    public void itemToTop(int position) {
        CheckItem itemToMove = cards.remove(position);
        Collections.reverse(cards);
        cards.add(itemToMove);
        Collections.reverse(cards);
        notifyDataSetChanged();
    }
    public void itemToEnd(int position) {
        CheckItem itemToMove = cards.remove(position);
        cards.add(itemToMove);
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        cards.remove(position);
        notifyDataSetChanged();
    }


   */

    public void setItem(NoteItem other_cards) {
        cards.add(other_cards);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterNotes.MyViewHolderNotes holder, int position) {
        holder.title.setText(this.cards.get(position).getTitle());
        holder.date.setText(getDateAsString(this.cards.get(position).getDate()));
        holder.description.setText(this.cards.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public static class MyViewHolderNotes extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        TextView description;

        public MyViewHolderNotes(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.note_title);
            this.date = itemView.findViewById(R.id.note_date);
            this.description = itemView.findViewById(R.id.note_description);
        }
    }
    private String getDateAsString(Date date){
        DateFormat df = new SimpleDateFormat("dd MMM ");
        return  df.format(date) + String.valueOf(date.getYear());

    }
}
