package com.example.myapplication.utils;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.CheckItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<CheckItem> cards;

    public RecyclerViewAdapter(ArrayList<CheckItem> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_list_item,parent,false);
        return new MyViewHolder(view);
    }

    public void setItems(Collection<CheckItem> other_cards) {
        cards.addAll(other_cards);
        notifyDataSetChanged();
    }
    public void toggleItem (int position) {
        cards.get(position).setIsComplete(!cards.get(position).getIsComplete());
        notifyItemChanged(position);
    }

    public ArrayList<CheckItem> getTodoListAsArrayList() {
        return cards;
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


    public void setItem(CheckItem other_cards) {
        cards.add(other_cards);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.task.setText(this.cards.get(position).getTask());
        holder.is_complete.setChecked((this.cards.get(position).getIsComplete()));
        if (holder.is_complete.isChecked()){
            holder.task.setPaintFlags( holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        }else {
            holder.task.setPaintFlags( holder.task.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG ));
        }

    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView task;
        CheckBox is_complete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.task = itemView.findViewById(R.id.task);
            this.is_complete = itemView.findViewById(R.id.is_complete);
        }
    }
}
