package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.DBContract.NotesTable;

public class NotesDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "notes.db";
    private final static int DATABASE_VERSION = 1;




    public NotesDBHelper (Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TASK_TABLE = "CREATE TABLE "
                                        + NotesTable.TABLE_NAME + " ( "
                                        + NotesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                        + NotesTable.COLUMN_TITLE + " TEXT NOT NULL, "
                                        + NotesTable.COLUMN_DATE + " INTEGER NOT NULL, "
                                        + NotesTable.COLUMN_DESCRIPTION + " TEXT NOT NULL );";
        db.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Если не совпадают версии таблиц удаляем старую
        db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NAME );
        // Теперь создаем новую
        onCreate(db);
    }
}
