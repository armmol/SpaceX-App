package com.example.restapiapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

@Dao
public interface SpaceXDao {
    @Insert
    void insert(TableRow tableRow);

    @Update
    void update(TableRow tableRow);

    @Delete
    void Delete(TableRow tableRow);

    @Delete
    void deleteAllRows(List<TableRow> data);

    @Query ("SELECT * FROM `SPACE-X TABLE` ORDER BY name ASC")
    List<TableRow> getALLRows();
}
