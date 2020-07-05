package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.CONSTANTS;
import com.example.myapplication.activities.NoteCreateActivity;
import com.example.myapplication.data.AppDataBase;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.ObservableRxList;
import com.example.myapplication.utils.RecyclerItemClickListener;
import com.example.myapplication.adapters.RecyclerViewAdapterNotes;
import com.jakewharton.rxbinding4.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class NotesFragment  extends Fragment {
    public  RecyclerViewAdapterNotes recyclerViewAdapterNotes;
    private CompositeDisposable disposableList = new CompositeDisposable();
    private AppDataBase dataBase;
    private ObservableRxList<NoteItem> cards;

    public  RecyclerView recyclerView;
    private SearchView searchField;

    public NotesFragment (ObservableRxList<NoteItem> cards){
        this.cards = cards;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) return;
        if(requestCode == CONSTANTS.REQUEST_CODE_EDIT_NOTE){
            if (resultCode == RESULT_OK) {
                NoteItem item = (NoteItem) data.getSerializableExtra(CONSTANTS.EXTRA_EDIT_NOTE);
                if (item == null) return;
                int index = (int) data.getSerializableExtra(CONSTANTS.EXTRA_EDIT_NOTE_INDEX);
                this.recyclerViewAdapterNotes.insertItem(index, item);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.note_list_layout,container,false);
        recyclerView = view.findViewById(R.id.recycler_notes_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        this.dataBase = AppDataBase.getInstance(this.getContext());

        recyclerViewAdapterNotes = new RecyclerViewAdapterNotes(cards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapterNotes);
        searchField = view.findViewById(R.id.search_note);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView.getContext(),
                recyclerView, (view1, position) -> showMenu(view1,position)));


                disposableList.add(RxSearchView.queryTextChanges(searchField)
                        .skip(1)
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .map(charSequence -> charSequence.toString().trim())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.io())
                        .flatMap((Function<String, Observable<RecyclerView.Adapter>>) search ->
                                Observable.create(emitter -> {
                                    List<NoteItem> notes = recyclerViewAdapterNotes.searchInNotes(search);
                                    emitter.onNext(RecyclerViewAdapterNotes.setItems(notes));
                                    emitter.onComplete();
                                }))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(Throwable::printStackTrace)
                        .subscribe(adapter -> recyclerView.setAdapter(adapter)
                        ));

        disposableList.add(this.cards.getObservable()
                .subscribeOn(Schedulers.io())
                .debounce(800,TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(list-> dataBase.saveNotes(list)));


        return view;
    }


    public void showMenu(View anchor, int index) {
        PopupMenu popup = new PopupMenu(anchor.getContext(), anchor);
        popup.getMenuInflater().inflate(R.menu.custom_menu, popup.getMenu());
        popup.getMenu().getItem(0).setOnMenuItemClickListener(v->{
            NoteItem itemToEdit = recyclerViewAdapterNotes.getItem(index);
            Intent intent = new Intent(this.getContext(), NoteCreateActivity.class);
            intent.putExtra(CONSTANTS.EXTRA_EDIT_NOTE, itemToEdit );
            intent.putExtra(CONSTANTS.EXTRA_EDIT_NOTE_INDEX, index );
            startActivityForResult(intent, CONSTANTS.REQUEST_CODE_EDIT_NOTE);
            return true;
        });
        popup.getMenu().getItem(1).setOnMenuItemClickListener(v->{
            recyclerViewAdapterNotes.deleteItem(index);
            return true;
        });
        popup.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposableList.clear();
    }
}
