package com.example.menukorvet.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuDao {

    @Query("SELECT * FROM menu")
    LiveData<List<MenuItem>> getAllMenu();

    @Query("SELECT * FROM menu WHERE id_abk = :numberABK")
    LiveData<List<MenuItem>> getAllMenuABK(int numberABK);

    @Query("DELETE FROM menu")
    void clearAllMenu();

    @Insert
    void insertMenu(MenuItem menu);
}
