package com.example.myapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.NoteItem;
import com.example.myapplication.utils.DateConverter;

import java.util.Calendar;

public class NoteCreateActivity extends AppCompatActivity {
    private EditText title;
    private EditText description;
    private Button chooseDate;
    private Button createNote;
    private Dialog calendarDialog;
    private NoteItem noteItem;
    private SharedPreferences UserSettings;
    private final DateConverter dateConverter = new DateConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserSettings =  getSharedPreferences(getString(R.string.APP_PREFERENCES),
                Context.MODE_PRIVATE);
        if (UserSettings.contains(CONSTANTS.THEME) && UserSettings.getBoolean(CONSTANTS.THEME,false)){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        title = findViewById(R.id.note_header);
        description = findViewById(R.id.main_note);
        chooseDate = findViewById(R.id.show_date);
        chooseDate.setText(dateConverter.getCurrentDateAsString());
        createNote = findViewById(R.id.create_note);
        calendarDialog = new Dialog(this);
        noteItem = new NoteItem();
        Intent intentFromMenu = getIntent();
        NoteItem itemToEdit = (NoteItem) intentFromMenu.getSerializableExtra(CONSTANTS.EXTRA_EDIT_NOTE);
        if (itemToEdit != null){
            this.title.setText( itemToEdit.getTitle() );
            this.description.setText( itemToEdit.getDescription() );
            this.chooseDate.setText( DateConverter.getDateAsString(itemToEdit.getDate()));
        }

        chooseDate.setOnClickListener(this::showCalendarDialog);
        createNote.setOnClickListener((view)->{

            buildNoteFromForm();

            if (!noteItem.isEmpty()){
                if (itemToEdit != null){
                    intentFromMenu.putExtra(CONSTANTS.EXTRA_EDIT_NOTE, noteItem);
                    setResult(RESULT_OK, intentFromMenu);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra(CONSTANTS.EXTRA_NOTE_ITEM, noteItem);
                    setResult(RESULT_OK, intent);
                }
            }
            finish();
        });
    }

    private void buildNoteFromForm(){
        String titleStr = title.getText().toString().trim();
        String descriptionStr = description.getText().toString().trim();
        if ((!titleStr.equals("")) || (!descriptionStr.equals(""))){
            noteItem.setTitle(titleStr);
            noteItem.setDescription(descriptionStr);
        }
        if (noteItem.getDate() == null) {
            noteItem.setDate(Calendar.getInstance().getTime());
        }

    }

    private void showCalendarDialog(View view){
        calendarDialog.setContentView(R.layout.calendar);

        Button selectDate = calendarDialog.findViewById(R.id.select_date);
        DatePicker picker = calendarDialog.findViewById(R.id.datePicker);
        selectDate.setOnClickListener((v)->{
            chooseDate.setText(dateConverter.getDateFromDatePickerAsString(picker));
            noteItem.setDate(dateConverter.getDateFromDatePicker(picker));
            calendarDialog.dismiss();
        });
        calendarDialog.show();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
