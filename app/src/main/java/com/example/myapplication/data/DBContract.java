package com.example.myapplication.data;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract(){}

    public static final class TaskTable implements BaseColumns{
        public final static String TABLE_NAME = "quick_notes";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TASK = "task";
        public final static String COLUMN_CHECKED = "checked";

        public final static int CHECKED = 1;
        public final static int NOT_CHECKED = 0;

    }

    public static final class NotesTable implements BaseColumns{
        public final static String TABLE_NAME = "big_notes";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_DESCRIPTION = "description";
    }

}
