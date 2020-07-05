package com.example.myapplication.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener{
        //void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    private GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context,
                                     final RecyclerView recyclerView,
                                     OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            /*@Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }*/

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child != null && onItemClickListener != null){
                    onItemClickListener.onLongItemClick(child,
                            recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(),e.getY());
        if (childView != null && gestureDetector.onTouchEvent(e)){
            //onItemClickListener.onItemClick(childView,rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}