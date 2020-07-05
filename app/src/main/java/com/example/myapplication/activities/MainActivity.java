package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;
import com.example.myapplication.data.AppDataBase;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.models.NoteItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private  List< CheckItem > tasks;
    private  List< NoteItem > notes;
    private  Disposable db_dispose;
    private  LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences UserSettings = getSharedPreferences(getString(R.string.APP_PREFERENCES),
                Context.MODE_PRIVATE);
        if (UserSettings.contains(CONSTANTS.THEME) && UserSettings.getBoolean(CONSTANTS.THEME,false)){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        animationView = findViewById(R.id.animation);
        Observable<Object> db_obs = Observable.create(emitter -> {
            final AppDataBase database = AppDataBase.getInstance(this);
            this.tasks = database.checkDao().loadAllTasks();
            this.notes = database.noteDao().loadAllNotes();
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread());


        boolean isFirstTime = UserSettings.getBoolean(CONSTANTS.IS_FIRST_TIME,true);

        if (!isFirstTime) db_dispose = db_obs.subscribe();


        animationView.addAnimatorListener( new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent;
                if (isFirstTime){
                    intent = new Intent(MainActivity.this,Instruction.class);
                } else{
                    intent = new Intent(MainActivity.this, MainDashBoard.class);
                    intent.putExtra(CONSTANTS.EXTRA_TASKS, (ArrayList) tasks );
                    intent.putExtra(CONSTANTS.EXTRA_NOTES, (ArrayList) notes );
                    db_dispose.dispose();
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
