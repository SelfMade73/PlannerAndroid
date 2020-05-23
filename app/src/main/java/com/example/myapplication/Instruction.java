package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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

        tabLayout = findViewById(R.id.tab_indicator);
        viewPager   = findViewById(R.id.intro_view_pager);
        nextButton = findViewById(R.id.button_instruction_next);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,introScreensList);
        viewPager.setAdapter(introViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                if (position == 1)
                {
                    nextButton.setText("start now");
                }
                if ( position == 2 ){
                    Intent intent = new Intent(Instruction.this,MainDashBoard.class);
                    startActivity(intent);
                    finish();
                }
                else viewPager.setCurrentItem(++position);
            }
        });

    }
}
