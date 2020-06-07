package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.myapplication.R;
import com.example.myapplication.data.DBContract.*;
import com.example.myapplication.data.NotesDBHelper;
import com.example.myapplication.data.TaskDbHelper;
import com.example.myapplication.fragments.CheckListFragment;
import com.example.myapplication.fragments.NotesFragment;
import com.example.myapplication.models.CheckItem;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.IntroViewPagerAdapter;
import com.example.myapplication.utils.MainViewPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class MainDashBoard extends AppCompatActivity {
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private SharedPreferences UserSettings;
    private FloatingActionButton fab;
    private IntroViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView menuButton;
    private Switch colorTheme;
    private Switch notificationsSwitch;
    private Dialog createNote;
    private Dialog calendar;
    private DatePicker datePicker;
    private NotesFragment notesFragment;
    private TaskDbHelper taskDbHelper;
    private SQLiteDatabase db;
    private CheckListFragment checkFragment;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if(requestCode == CONSTANTS.REQUEST_CODE_CREATE_NOTE){
            switch (resultCode){
                case RESULT_CANCELED: return;
                case RESULT_OK:
                    NoteItem noteItem = (NoteItem) data.getSerializableExtra("NoteItem");
                    notesFragment.recyclerViewAdapterNotes.setItem(noteItem);
                    viewPager.setCurrentItem(1);
                    notesFragment.recyclerView.smoothScrollToPosition(notesFragment.
                                    recyclerViewAdapterNotes.getItemCount() - 1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        Window window = getWindow();
        //transparent status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.tab_layout_main);
        taskDbHelper = new TaskDbHelper(this);
        db = taskDbHelper.getReadableDatabase();


        menuButton = findViewById(R.id.expanded_menu);
        fab = findViewById(R.id.fab_new_note);
        createNote = new Dialog(this);
        createNote.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        calendar = new Dialog(this);
        calendar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        colorTheme = findViewById(R.id.color_theme);
        notificationsSwitch = findViewById(R.id.notifications);

        UserSettings = getSharedPreferences(getString(R.string.APP_PREFERENCES),
                                                             Context.MODE_PRIVATE);


        colorTheme.setChecked(UserSettings.getBoolean("theme", false));
        notificationsSwitch.setChecked(UserSettings.getBoolean("notify", true));


        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());


        //Adding fragments
        final ArrayList<CheckItem> checkItems = getTasksFromDB();

        ArrayList<NoteItem> noteItems = new ArrayList<>();
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfвфывфывфывфывksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));
        noteItems.add(new NoteItem("Про мою жизнь",
                "skfksemfeksmfksmfksf", Calendar.getInstance().getTime()));




        checkFragment = new CheckListFragment(checkItems);
        notesFragment = new NotesFragment(noteItems);

        mainViewPagerAdapter.addFragment(checkFragment,getString(R.string.quick_notes));
        mainViewPagerAdapter.addFragment(notesFragment,getString(R.string.big_notes));
        mainViewPagerAdapter.addFragment(new CheckListFragment(checkItems),getString(R.string.stat));





        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetCallback() {
              @Override
              public void onStateChanged(@NonNull View bottomSheet, int newState) {

              }
              @Override
              public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();

              }
        }


        );

        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(MainDashBoard.this, NoteCreateActivity.class);
            startActivityForResult(intent,CONSTANTS.REQUEST_CODE_CREATE_NOTE);
        });



        menuButton.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            else
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        colorTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                this.UserSettings.edit().putBoolean("theme",isChecked).apply());
        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                this.UserSettings.edit().putBoolean("notify",isChecked).apply());
    }



    //TODO remake with Room ( https://developer.android.com/training/data-storage/room )
    private ArrayList<CheckItem> getTasksFromDB(){
        TaskDbHelper taskDbHelperToRead = new TaskDbHelper(this);
        SQLiteDatabase db_read = taskDbHelperToRead.getReadableDatabase();
        ArrayList<CheckItem> result = new ArrayList<>();
        String SQL_GET_TASK = "SELECT " + TaskTable.COLUMN_TASK + ", "
                + TaskTable.COLUMN_CHECKED + " FROM " + TaskTable.TABLE_NAME + ";";

        Cursor cursor = db_read.rawQuery(SQL_GET_TASK, null);
        while (cursor.moveToNext()){
            CheckItem item = new CheckItem();
            item.setTask( cursor.getString( cursor.getColumnIndex( TaskTable.COLUMN_TASK ) ) );
            item.setIsComplete( cursor.getInt( cursor.getColumnIndex( TaskTable.COLUMN_CHECKED ) ) == 1 );
            result.add( item );
        }
        cursor.close();
        return result;
    }

    private void saveTasksToDB(ArrayList<CheckItem> tasks){
        TaskDbHelper taskDbHelperToWrite = new TaskDbHelper(this);
        SQLiteDatabase db_write = taskDbHelperToWrite.getWritableDatabase();
        String SQL_TRUNCATE_DB = "DELETE FROM " + TaskTable.TABLE_NAME + " ;";
        db_write.execSQL(SQL_TRUNCATE_DB);
        ContentValues contentValues = new ContentValues();
        for ( CheckItem item : tasks){
            contentValues.put( TaskTable.COLUMN_TASK, item.getTask() );
            contentValues.put( TaskTable.COLUMN_CHECKED, item.getIsComplete() );
            db_write.insert( TaskTable.TABLE_NAME, null, contentValues );
        }
    }

    private ArrayList <NoteItem> getNotesFromDB (){
        ArrayList <NoteItem > result = new ArrayList<>();
        NotesDBHelper notesDBHelperToRead = new NotesDBHelper(this);
        SQLiteDatabase db_read = notesDBHelperToRead.getReadableDatabase();
        String SQL_GET_NOTES = " SELECT "
                                + NotesTable.COLUMN_TITLE + ", "
                                + NotesTable.COLUMN_DATE + ", "
                                + NotesTable.COLUMN_DESCRIPTION + " FROM "
                                + NotesTable.TABLE_NAME + " ;";
        Cursor cursor = db_read.rawQuery(SQL_GET_NOTES, null);
        while (cursor.moveToNext()){
            NoteItem item = new NoteItem();
            item.setTitle(cursor.getString(cursor.getColumnIndex(NotesTable.COLUMN_TITLE)));
            item.setDateFromInteger(cursor.getInt(cursor.getColumnIndex(NotesTable.COLUMN_DATE)));
            item.setDescription(cursor.getString(cursor.getColumnIndex(NotesTable.COLUMN_DESCRIPTION)));
            result.add( item );
        }
        cursor.close();
        return result;
    }


    private void saveNotesToDB ( ArrayList <NoteItem> notes ){
        NotesDBHelper notesDBHelperToWrite = new NotesDBHelper(this);
        SQLiteDatabase db_write = notesDBHelperToWrite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (NoteItem item : notes){
            contentValues.put(NotesTable.COLUMN_TITLE, item.getTitle());
            contentValues.put(NotesTable.COLUMN_DATE, item.getDateAsLong());
            contentValues.put (NotesTable.COLUMN_DESCRIPTION, item.getDescription());
            db_write.insert(NotesTable.TABLE_NAME, null,contentValues);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveTasksToDB( checkFragment.recyclerViewAdapter.getTodoListAsArrayList() );
    }

    private void showCreateNoteDialog (View view){
        createNote.setContentView(R.layout.dialog_note_create);
        Button openCalendar = createNote.findViewById(R.id.open_calendar);
        openCalendar.setOnClickListener((v)-> showCalendarDialog(v));
        createNote.show();
    }

    private void showCalendarDialog (View view){

        //calendar.show();
    }



}
