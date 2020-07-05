package com.example.myapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.dao.CheckDao;
import com.example.myapplication.data.dao.NoteDao;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.models.NoteItem;

import java.util.List;

@Database(entities = { CheckItem.class, NoteItem.class }, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    public abstract CheckDao checkDao();
    public abstract NoteDao  noteDao();

    private final static String DB_NAME = "planer_dp";

    public synchronized void saveTasks(List<CheckItem> list){
        this.checkDao().deleteAllFromTasks();
        this.checkDao().insertOrReplaceTask(list);
    }

    public synchronized void saveNotes(List<NoteItem> list){
        this.noteDao().deleteAllFromNotes();
        this.noteDao().insertOrReplaceNotes(list);
    }


    public static synchronized AppDataBase getInstance(Context context){
        if ( INSTANCE == null ){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
