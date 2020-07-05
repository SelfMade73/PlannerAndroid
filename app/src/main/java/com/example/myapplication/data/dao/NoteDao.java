package com.example.myapplication.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.NoteItem;

import java.util.List;

import static androidx.room.OnConflictStrategy.*;

@Dao
public interface NoteDao  {

    @Insert(onConflict = REPLACE)
    void  insertNote(NoteItem note);

    @Insert(onConflict = REPLACE)
    void insertOrReplaceNotes(Iterable<NoteItem> notes);

    @Update
    void updateNoteById(NoteItem note);

    @Query("DELETE FROM notes WHERE id LIKE :id")
    void deleteNoteById(int id);

    @Query("SELECT * FROM notes")
    List<NoteItem> loadAllNotes();

    @Query("DELETE FROM notes")
    void deleteAllFromNotes();
}
