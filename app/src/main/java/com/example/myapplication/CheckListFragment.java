package com.example.myapplication;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CheckListFragment  extends Fragment {

    private ArrayList<CheckItem> cards;
    public RecyclerViewAdapter recyclerViewAdapter;
    private Button addCheckItem;
    private EditText editText;
    private View checkItem;
    private Dialog editDialog;


    public CheckListFragment(ArrayList<CheckItem> cards){
        this.cards = cards;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.check_list_layout,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_check_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(cards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        editText = view.findViewById(R.id.new_check);
        checkItem = view.findViewById(R.id.is_complete);
        addCheckItem = view.findViewById(R.id.add_check);
        addCheckItem.setOnClickListener((v) -> {
            String newTaskToCheckList = editText.getText().toString();
            if (! newTaskToCheckList.equals("")){
                recyclerViewAdapter.setItem(new CheckItem(newTaskToCheckList));
                editText.setText("");
                recyclerView.scrollToPosition(recyclerViewAdapter.getItemCount() - 1);
            }
        });
        editDialog = new Dialog(getContext());
        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(recyclerView.getContext(),
                                             recyclerView,
                                             new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                CheckBox checkBox = view.findViewById(R.id.is_complete);
                TextView textView = view.findViewById(R.id.task);
                checkBox.setChecked(!checkBox.isChecked());
                recyclerViewAdapter.toggleItem(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showEditDialog(view, position);
            }
        }));


        return view;
    }
    public void showEditDialog (View view ,int position){
        editDialog.setContentView(R.layout.check_pop_up);
        Button toTop = editDialog.findViewById(R.id.todo_to_top);
        Button toEnd = editDialog.findViewById(R.id.todo_to_end);
        Button delete = editDialog.findViewById(R.id.todo_delete);

        toTop.setOnClickListener((v)-> {
            recyclerViewAdapter.itemToTop(position);
            editDialog.dismiss();
        });
        toEnd.setOnClickListener((v)-> {
            recyclerViewAdapter.itemToEnd(position);
            editDialog.dismiss();
        });
        delete.setOnClickListener((v)-> {
            recyclerViewAdapter.deleteItem(position);
            editDialog.dismiss();
        });
        editDialog.show();

    }



    @Override
    public void onStop() {
        super.onStop();
        File todoFile = new File( getContext().getFilesDir(),CONSTANTS.todoFileName);
        try {
            BufferedWriter todoWriter = new BufferedWriter(new FileWriter(todoFile, false));
            ArrayList<String> todo = recyclerViewAdapter.getTodoListAsStringList();
            for (String str : todo) {
                todoWriter.write(str + "\n");
               // todoWriter.newLine();
            }
            todoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
