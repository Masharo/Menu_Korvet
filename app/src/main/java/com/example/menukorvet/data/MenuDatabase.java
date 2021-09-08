package com.example.menukorvet.data;

import android.content.Context;
import android.database.DatabaseUtils;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Objects;

@Database(entities = {MenuItem.class}, version = 1, exportSchema = false)
public abstract class MenuDatabase extends RoomDatabase {

    private static final String NAME = "menu_db";
    private static MenuDatabase menuDatabase;

    public static synchronized MenuDatabase getInstance(Context context) {
        if (Objects.isNull(menuDatabase)) {
            menuDatabase = Room.databaseBuilder(context, MenuDatabase.class, NAME).build();
        }

        return menuDatabase;
    }

    public abstract MenuDao getMenuDao();
}
