package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class Instruction extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroViewPagerAdapter introViewPagerAdapter;


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


        viewPager = findViewById(R.id.intro_view_pager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,introScreensList);
        viewPager.setAdapter(introViewPagerAdapter);
    }
}
