package com.example.roomwordssample;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao

public interface WordDao {
    @Insert

    void insert(Word word);

    @Query("DELETE FROM word_table")

    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY word ASC")

    LiveData<List<Word>> getAllWords();

}
