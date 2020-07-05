package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.models.InstructionItem;
import com.example.myapplication.adapters.IntroViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Instruction extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private Button nextButton;
    SharedPreferences UserSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserSettings =  getSharedPreferences(getString(R.string.APP_PREFERENCES),
                Context.MODE_PRIVATE);
        if (UserSettings.contains(CONSTANTS.THEME) && UserSettings.getBoolean(CONSTANTS.THEME,false)){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        List<InstructionItem> introScreensList = new ArrayList<>();

        String [] titles = getResources().getStringArray(R.array.titles);
        String [] descriptions = getResources().getStringArray(R.array.descriptions);
        TypedArray img = getResources().obtainTypedArray(R.array.intro_img);

        for (int i = 0; i < 3 ;++i)
            introScreensList.add(new InstructionItem(titles[i],descriptions[i],img.getResourceId(i,0)));

        img.recycle();


        tabLayout = findViewById(R.id.tab_indicator);
        viewPager   = findViewById(R.id.intro_view_pager);
        nextButton = findViewById(R.id.button_instruction_next);
        adapter = new IntroViewPagerAdapter(this,introScreensList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        nextButton.setOnClickListener((view) -> {
            int position = viewPager.getCurrentItem();
            if ( position == adapter.getCount() - 1 ){
                Intent intent = new Intent(Instruction.this,MainDashBoard.class);
                UserSettings.edit().putBoolean( CONSTANTS.IS_FIRST_TIME, false ).apply();
                startActivity(intent);
                finish();
            }
            else viewPager.setCurrentItem(++position);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                nextButton.setText(getString((position < adapter.getCount() - 1) ?
                        R.string.instruction_next:
                        R.string.start_now ));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
