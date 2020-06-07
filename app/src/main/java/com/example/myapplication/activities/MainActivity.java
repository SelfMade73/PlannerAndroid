package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    class LoadFiles extends AsyncTask<Void,Void,ArrayList <String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> files = new ArrayList<>();
            File baseDirectory = new File(Environment.getExternalStorageDirectory() +
                    "/notesDir");
            if (baseDirectory.exists()){
                    String [] fileNames = baseDirectory.list() ;
                    if (fileNames != null) Collections.addAll(files, fileNames);
                }
                return files;
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getting user files if they exist
        LoadFiles loadFiles = new LoadFiles();
        final ArrayList <String> files = loadFiles.doInBackground();

        final LottieAnimationView animationView = findViewById(R.id.animation);

        SharedPreferences UserSettings = getSharedPreferences(getString(R.string.APP_PREFERENCES),
                                                              Context.MODE_PRIVATE);

        boolean isFirstTime = UserSettings.getBoolean("isFirstTime",true);



        animationView.addAnimatorListener( new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent;
                if (isFirstTime){
                    intent = new Intent(MainActivity.this,Instruction.class);
                } else{
                    intent = new Intent(MainActivity.this, MainDashBoard.class);
                    intent.putExtra("FileNames",files);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

    }

}