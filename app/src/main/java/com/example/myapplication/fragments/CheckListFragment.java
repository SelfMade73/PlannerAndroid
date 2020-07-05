package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RecyclerViewAdapterTasks;
import com.example.myapplication.data.AppDataBase;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.utils.*;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckListFragment  extends Fragment {

    private ObservableRxList<CheckItem> cards;
    public RecyclerViewAdapterTasks recyclerViewAdapter;
    public RecyclerView recyclerView;
    private Button addCheckItem;
    private EditText editText;
    private AppDataBase database;
    private CompositeDisposable disposableList = new CompositeDisposable();

    public CheckListFragment(ObservableRxList<CheckItem> cards){
        this.cards = cards;
        recyclerViewAdapter = new RecyclerViewAdapterTasks(cards);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.check_list_layout,container,false);
        recyclerView = view.findViewById(R.id.recycler_check_list);
        editText = view.findViewById(R.id.new_check);
        addCheckItem = view.findViewById(R.id.add_check);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);

        this.database = AppDataBase.getInstance(this.getContext());

        addCheckItem.setOnClickListener((v) -> {
            String newTaskToCheckList = editText.getText().toString().trim();
            if (! newTaskToCheckList.equals("")){
                CheckItem item = new CheckItem(newTaskToCheckList);
                recyclerViewAdapter.setItem(item);
                editText.setText("");
                recyclerView.scrollToPosition(recyclerViewAdapter.getItemCount() - 1);
            }

        });

        disposableList.add(cards.getObservable()
                .debounce(1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(list -> database.saveTasks(list)));

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(recyclerView.getContext(),
                recyclerView, (view1, position) -> {
                        CheckItem selectedItem = recyclerViewAdapter.getItem( position );
                        editText.setText( selectedItem.getTask() );
                }
        ));

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.cards.notifyAndUpdate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposableList.clear();
    }
}
