package com.example.menukorvet.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.menukorvet.pojo.Dish;
import com.example.menukorvet.pojo.DishAndFavorite;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.List;

@Dao
public interface MenuDao {

    @Query("SELECT * FROM dish")
    List<Dish> getAllMenu();

    @Query("SELECT * FROM dish WHERE abk = :numberABK")
    List<Dish> getAllMenuABK(int numberABK);

    @Query("DELETE FROM dish")
    void clearAllMenu();

    @Insert
    void insertMenu(List<Dish> menu);

    @Insert
    void insertFavorite(FavoriteDish favoriteDish);

    @Delete
    void deleteFavorite(FavoriteDish favoriteDish);

//    TODO
//    @Query("SELECT * " +
//            "FROM dish, favorite_dish " +
//            "WHERE dish.abk = :numberABK " +
//            "ORDER BY nameFavorite")

    @Query("SELECT * FROM dish WHERE dish.abk = :numberABK")
    List<DishAndFavorite> getDishAndFavorite(int numberABK);
}
