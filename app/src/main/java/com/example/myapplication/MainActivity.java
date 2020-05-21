package com.example.myapplication;

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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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


        animationView.addAnimatorListener( new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(MainActivity.this,Instruction.class);
                intent.putExtra("FileNames",files);
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
