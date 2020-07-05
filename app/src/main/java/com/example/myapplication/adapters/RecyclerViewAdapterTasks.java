package com.example.myapplication.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.TaskViewHolder;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.utils.ObservableRxList;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterTasks extends RecyclerView.Adapter<TaskViewHolder> {
    private ObservableRxList<CheckItem> cards;

    public RecyclerViewAdapterTasks(ObservableRxList<CheckItem> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_list_item,parent,false);
        return new TaskViewHolder(view);
    }

    public void toggleItem (int position) {
        cards.get(position).setComplete(!cards.get(position).getComplete());
        notifyItemChanged(position);
    }


    public void setItem(CheckItem other_cards) {
        cards.add(other_cards);
        notifyDataSetChanged();
    }

    public CheckItem getItem(int index) {
        return this.cards.get( index );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.getTask().setText(this.cards.get(position).getTask());
        holder.getIsComplete().setChecked((this.cards.get(position).getComplete()));
        if (holder.getIsComplete().isChecked()){
            holder.getTask().setPaintFlags( holder.getTask().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        }else {
            holder.getTask().setPaintFlags( holder.getTask().getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG ));
        }

        holder.getTask().setOnClickListener(click->this.toggleItem( position ));
        holder.getIsComplete().setOnClickListener(click->this.toggleItem( position ));

        holder.getBucket().setOnClickListener(click->this.scaleView(holder.itemView,
                                holder.itemView.getScaleX(),0,position)
        );

    }

    public void scaleView(View v, float startScale, float endScale,int position) {
        Animation anim = new ScaleAnimation(
                startScale, endScale,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);
        anim.setFillAfter(true);
        anim.setDuration(250);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                cards.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        v.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        return this.cards.size();
    }

}
