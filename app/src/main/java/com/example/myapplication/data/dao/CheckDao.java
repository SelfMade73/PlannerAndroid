package com.example.myapplication.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.CheckItem;

import java.util.List;

import static androidx.room.OnConflictStrategy.*;

@Dao
public interface CheckDao {
    @Insert(onConflict = REPLACE)
    void insertTask(CheckItem task);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceTask(Iterable<CheckItem> tasks);

    @Update
    void updateNoteById(CheckItem task);

    @Query("DELETE FROM tasks WHERE id LIKE :id")
    void deleteTaskById ( int id );

    @Query("SELECT * FROM tasks")
    List<CheckItem> loadAllTasks();

    @Query("DELETE FROM tasks")
    void deleteAllFromTasks();



}
