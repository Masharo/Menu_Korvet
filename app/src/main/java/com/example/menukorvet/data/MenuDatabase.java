package com.example.menukorvet.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.menukorvet.pojo.Dish;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.Objects;

@Database(entities = {Dish.class, FavoriteDish.class}, version = 1, exportSchema = false)
public abstract class MenuDatabase extends RoomDatabase {

    public static final String NAME = "menu_db";
    private static MenuDatabase menuDatabase;

    public static synchronized MenuDatabase getInstance(Context context) {
        if (Objects.isNull(menuDatabase)) {
            menuDatabase = Room.databaseBuilder(context, MenuDatabase.class, NAME).build();
        }

        return menuDatabase;
    }

    public abstract MenuDao getMenuDao();
}
