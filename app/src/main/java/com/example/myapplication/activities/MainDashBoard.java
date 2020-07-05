package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.data.AppDataBase;
import com.example.myapplication.fragments.CheckListFragment;
import com.example.myapplication.fragments.NotesFragment;
import com.example.myapplication.fragments.StatisticFragment;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.BarChartSetup;
import com.example.myapplication.adapters.MainViewPagerAdapter;
import com.example.myapplication.utils.ObservableRxList;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainDashBoard extends AppCompatActivity {
    private NotesFragment notesFragment;
    private CheckListFragment checkFragment;
    private StatisticFragment statisticFragment;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private SharedPreferences UserSettings;
    private AppDataBase database;
    private CompositeDisposable disposableList = new CompositeDisposable();

    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView menuButton;
    private Switch colorTheme;
    private Switch notificationsSwitch;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if(requestCode == CONSTANTS.REQUEST_CODE_CREATE_NOTE){
            if (resultCode == RESULT_OK) {
                NoteItem noteItem = (NoteItem) data.getSerializableExtra(CONSTANTS.EXTRA_NOTE_ITEM);
                notesFragment.recyclerViewAdapterNotes.setItem(noteItem);
                viewPager.setCurrentItem(1);
                notesFragment.recyclerView.smoothScrollToPosition(notesFragment.
                        recyclerViewAdapterNotes.getItemCount() - 1);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserSettings =  getSharedPreferences(getString(R.string.APP_PREFERENCES),
                Context.MODE_PRIVATE);
        if (UserSettings.contains(CONSTANTS.THEME) && UserSettings.getBoolean(CONSTANTS.THEME,false)){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        database = AppDataBase.getInstance(this);
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.tab_layout_main);

        Intent intent_data = getIntent();

        menuButton = findViewById(R.id.expanded_menu);
        fab = findViewById(R.id.fab_new_note);
        colorTheme = findViewById(R.id.color_theme);
        notificationsSwitch = findViewById(R.id.notifications);

        colorTheme.setChecked(UserSettings.getBoolean(CONSTANTS.THEME, false));
        notificationsSwitch.setChecked(UserSettings.getBoolean(CONSTANTS.NOTIFY, true));


        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        ArrayList checkItems = (ArrayList)intent_data.getSerializableExtra(CONSTANTS.EXTRA_TASKS);
        ArrayList noteItems = (ArrayList) intent_data.getSerializableExtra(CONSTANTS.EXTRA_NOTES);

        ObservableRxList<CheckItem> observableRxList = new ObservableRxList<>();
        ObservableRxList<NoteItem> observableRxListNotes = new ObservableRxList<>();

        observableRxList.setList( (checkItems != null) ? checkItems: new ArrayList<>() );
        observableRxListNotes.setList( (noteItems != null) ? noteItems: new ArrayList<>() );

        checkFragment =  new CheckListFragment(observableRxList);
        notesFragment = new NotesFragment(observableRxListNotes);
        statisticFragment = new StatisticFragment();

        mainViewPagerAdapter.addFragment(checkFragment,getString(R.string.quick_notes));
        mainViewPagerAdapter.addFragment(notesFragment,getString(R.string.big_notes));
        mainViewPagerAdapter.addFragment(statisticFragment,getString(R.string.stat));

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetCallback() {
              @Override
              public void onStateChanged(@NonNull View bottomSheet, int newState) {}
              @Override
              public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
              }
        });

        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(MainDashBoard.this, NoteCreateActivity.class);
            startActivityForResult(intent,CONSTANTS.REQUEST_CODE_CREATE_NOTE);
        });

        menuButton.setOnClickListener(v -> {
            if ((bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        colorTheme.setOnCheckedChangeListener((buttonView, isChecked) ->{
            this.UserSettings.edit().putBoolean(CONSTANTS.THEME,isChecked).apply();
            RestartApp ();
        });

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->{
                    this.UserSettings.edit().putBoolean(CONSTANTS.NOTIFY,isChecked).apply();
        });


        disposableList.add(observableRxList.getObservable()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .debounce(50,TimeUnit.MILLISECONDS)
                .doOnNext(list -> statisticFragment.updateChart(BarChartSetup.getDataSets(this,list)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.disposableList.clear();
    }

    private  void RestartApp (){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}