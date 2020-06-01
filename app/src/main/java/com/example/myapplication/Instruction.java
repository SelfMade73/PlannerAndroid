package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Instruction extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabLayout;
    private Button nextButton;
    SharedPreferences UserSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        @SuppressWarnings("unchecked")
        ArrayList<String> questions = (ArrayList<String>)getIntent().getSerializableExtra("FileNames");
        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        List<InstructionItem> introScreensList = new ArrayList<>();
        introScreensList.add(new InstructionItem("Save time",
                "Time is the most important thing\n that we have",
                R.drawable.stopwatch));
        introScreensList.add(new InstructionItem("Plan your purchases",
                "It helps you save more money",
                R.drawable.shopping));
        introScreensList.add(new InstructionItem("Write quick notes",
                "Your future is under control",
                R.drawable.note));
        UserSettings =  getSharedPreferences(getString(R.string.APP_PREFERENCES),
                                                        Context.MODE_PRIVATE);
        tabLayout = findViewById(R.id.tab_indicator);
        viewPager   = findViewById(R.id.intro_view_pager);
        nextButton = findViewById(R.id.button_instruction_next);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,introScreensList);
        viewPager.setAdapter(introViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        nextButton.setOnClickListener((view) -> {
            int position = viewPager.getCurrentItem();
            if ( position == 2 ){
                Intent intent = new Intent(Instruction.this,MainDashBoard.class);
                UserSettings.edit().putBoolean("isFirstTime",false).apply();
                startActivity(intent);
                finish();
            }
            else viewPager.setCurrentItem(++position);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ((position < 2)) {
                    nextButton.setText(getString(R.string.instruction_next));
                } else {
                    nextButton.setText(getString(R.string.start_now));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
