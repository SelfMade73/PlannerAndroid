package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.data.DBContract.*;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TASK_TABLE =
                "CREATE TABLE " + TaskTable.TABLE_NAME + " ("
                + TaskTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskTable.COLUMN_TASK + " TEXT NOT NULL, "
                + TaskTable.COLUMN_CHECKED + " INTEGER NOT NULL DEFAULT 0);";
        //Создание таблицы
        db.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Если не совпадают версии таблиц удаляем старую
        db.execSQL("DROP TABLE IF EXISTS " + TaskTable.TABLE_NAME );
        // Теперь создаем новую
        onCreate(db);
    }
}
