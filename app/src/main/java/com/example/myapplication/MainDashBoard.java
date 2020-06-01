package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainDashBoard extends AppCompatActivity {
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private SharedPreferences UserSettings;
    private FloatingActionButton fab;
    private IntroViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView menuButton;
    private Switch colorTheme;
    private Switch notificationsSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.tab_layout_main);

        menuButton = findViewById(R.id.expanded_menu);
        fab = findViewById(R.id.fab_new_note);

        colorTheme = findViewById(R.id.color_theme);
        notificationsSwitch = findViewById(R.id.notifications);

        UserSettings = getSharedPreferences(getString(R.string.APP_PREFERENCES),
                                                             Context.MODE_PRIVATE);


        colorTheme.setChecked(UserSettings.getBoolean("theme", false));
        notificationsSwitch.setChecked(UserSettings.getBoolean("notify", true));


        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());


        //Adding fragments
        final ArrayList<CheckItem> checkItems = getTodoFromFile();


        final CheckListFragment checkFragment = new CheckListFragment(checkItems);

        mainViewPagerAdapter.addFragment(checkFragment,getString(R.string.quick_notes));
        mainViewPagerAdapter.addFragment(new CheckListFragment(checkItems),getString(R.string.big_notes));
        mainViewPagerAdapter.addFragment(new CheckListFragment(checkItems),getString(R.string.stat));


        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetCallback() {
              @Override
              public void onStateChanged(@NonNull View bottomSheet, int newState) {

              }
              @Override
              public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();

              }
        }


        );

        fab.setOnClickListener((view) -> checkFragment.recyclerViewAdapter.setItem(new CheckItem("Is it victory?")));

        menuButton.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            else
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        colorTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                this.UserSettings.edit().putBoolean("theme",isChecked).apply());
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                this.UserSettings.edit().putBoolean("notify",isChecked).apply());
    }

    private ArrayList<CheckItem> getTodoFromFile()  {
        ArrayList<CheckItem> itemsFromFile = new ArrayList<>();
        File todoFile = new File (getFilesDir(),CONSTANTS.todoFileName);
        try {
            BufferedReader todoReader = new BufferedReader(new FileReader(todoFile));
            String str;
            while((str=todoReader.readLine())!=null){
                if (str.startsWith("1")){
                    itemsFromFile.add(new CheckItem(str.substring(1),true));
                }else {
                    itemsFromFile.add(new CheckItem(str.substring(1),false));
                }
            }
            todoReader.close();
        }catch (IOException e){
           e.printStackTrace();
        }
        return itemsFromFile;
    }
}
