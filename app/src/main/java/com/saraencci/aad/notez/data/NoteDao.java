package com.saraencci.aad.notez.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);
    @Query("DELETE FROM note")
    void  delete();
}
